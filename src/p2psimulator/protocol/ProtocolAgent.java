package p2psimulator.protocol;
import p2psimulator.element.Node;
import p2psimulator.element.Queue;
import p2psimulator.util.Status;
import p2psimulator.util.DistributionInfo;
import p2psimulator.util.DistributionTypes;
import p2psimulator.prng.Distribution;
import p2psimulator.command.*;
import p2psimulator.Simulator;
import p2psimulator.protocol.message.*;
import p2psimulator.util.TopologyReader;
import p2psimulator.util.Preferences;
import p2psimulator.util.QueryTypes;
import p2psimulator.element.FileShared;
import java.io.*;
import java.util.*;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class ProtocolAgent {

  // Define constants for the states this protocol can be in. This defines
  // the P2P Protocol's finite state machine

  /*********** PEER STATUS ********/
  // Inactive Peer ...
  private final static int DOWN = 0;
  // Active Peer ...
  private final static int UP = 1;

  /********** PEER STATES ********/
  // Am I connected to P2P Network ...
  private boolean isConnected;



  // Looking For Neighbors ...
  private final static int DISCOVER_NEIGHBORS = 1;

  // Passive open, waiting for messages ...
  private final static int PARTICIPATE = 2;

   // Time Wait ...
  private final static int TIME_WAIT = 3;

  /********* PEER MESSAGES ******/

  private final static int PING = 1;

  private final static int PONG = 2;

  private final static int QUERY = 3;

  private final static int QUERY_HIT = 4;

  private final static int DATA_TRANSFER = 5;

  /********* TIMERS **************/

  private final static int NEIGHBOR_DISCOVERY_TIMER = 1;

  private final static int HEARTBEAT_TIMER = 2;

  private final static int QUERY_WAIT_TIMER = 3;

  // The local port of this connection and the remote port
  private int m_local_port;
  private int m_remote_port;

  // The current state we are in, one of the constants above.
  private int currentState;
  private Vector msgQueue;
  private Node node;
  private String protocol;
  private Vector timerList;
  private Vector myCurrentPeers;
  private boolean print;

  public ProtocolAgent(Node node, String protocolSimulated) {
    this.node = node;
    this.protocol = new String(protocolSimulated);
    this.msgQueue = new Vector();
    initialize();
  }

  private void initialize() {
    System.out.println("Node "+node.getNodeId()+": Starting "+this.protocol+" agent ...");
    this.isConnected = false;
    this.timerList = new Vector();
    this.myCurrentPeers = new Vector();
    currentState = DISCOVER_NEIGHBORS;
    this.print = true;
  }

  public void run() {
    int nodeStatus;
    double time;
    if(node.continueSimulation()) {
      nodeStatus = node.getStatus();
      time = Simulator.getInstance().getTime();
      switch(nodeStatus) {
        case Status.UP:
          uptimeProtocol(time);
          break;

        case Status.DOWN:
          downtimeProtocol(time);
          break;
      }
    }
  } // End of ProtocolAgent.run()

  private void uptimeProtocol(double time) {
    //printInfo(time, new String("in uptimeProtocol()"));
    switch(currentState) {
      case DISCOVER_NEIGHBORS:
        discoverNeighbors(time);
        break;
      case PARTICIPATE:
        collaboratingPeer(time);
        break;
    }
  }


  private void downtimeProtocol(double time) {
    this.msgQueue.removeAllElements(); // Will Discard All Messages as it is not a Peer ...
  }

  private void discoverNeighbors(double time) {
    //printInfo(time, new String("in discoverNeighbors()"));
    Vector neighbors = node.getNodeNeighbors();
    int sender = node.getNodeId();
    TopologyReader topoReader = (TopologyReader)(node.getTopologyReader());
    for(int index = 0; index <  neighbors.size(); index++) {
      int receiver = ((Integer)(neighbors.elementAt(index))).intValue();
      Node receiverNode = topoReader.getTopologyInfoForNode(receiver);
      double delay = topoReader.delayLatencyBetween(node.getNodeId(), receiver, (MessageConstants.PING_MSG_SIZE_MULTIPLE_MIN)*(Preferences.default_MTU));;
      double receiveTime = time + delay;

      // Send Ping Message ...
      printInfo(time, new String("is sending PING to "+receiver+" with delay "+delay));
      PingMessage pingMessage = new PingMessage(sender, receiver, time);
      SendMessageCommand sendCommand = new SendMessageCommand(receiveTime, receiverNode, pingMessage);
      send(sendCommand);
    }
    startTimer(NEIGHBOR_DISCOVERY_TIMER);
    currentState = PARTICIPATE;
  }

  private void collaboratingPeer(double simTime) {
    // Should Reply to PING and QUERY Messages ...
    //printInfo(simTime, new String("in collaboratingPeer()"));
    Vector pingMessages = processReceivedMessages(MessageConstants.PING);
    Vector queryMessages = processReceivedMessages(MessageConstants.QUERY);
    TopologyReader topoReader = (TopologyReader)(node.getTopologyReader());
    if(!pingMessages.isEmpty()) {
      // Reply to Ping Messages ...
      for(int index = 0; index < pingMessages.size(); index++) {
        PingMessage pingMessage = (PingMessage)(pingMessages.elementAt(index));
        int receiver = pingMessage.getSender();
        int sender = node.getNodeId();
        Node receiverNode = topoReader.getTopologyInfoForNode(receiver);
        double delay = topoReader.delayLatencyBetween(node.getNodeId(), receiver, (MessageConstants.PONG_MSG_SIZE_MULTIPLE_MIN)*(Preferences.default_MTU));
        double receiveTime = simTime + delay;

        // Send Pong message ...
        printInfo(simTime, new String("is sending PONG to "+receiver+" with delay "+delay));
        PongMessage pongMessage = new PongMessage(sender, receiver, simTime);
        SendMessageCommand sendCommand = new SendMessageCommand(receiveTime, receiverNode, pongMessage);
        send(sendCommand);
      }
    }

    if(!queryMessages.isEmpty()) {
      // Reply to Query Messages ...
      // If QueryHit, reply to Source, else decrement TTL, check TTL != 0, and then forward Query to next hop neighbor ...
      for (int queryIndex = 0; queryIndex < queryMessages.size(); queryIndex++) {
        QueryMessage queryMessage = (QueryMessage)(queryMessages.elementAt(queryIndex));
        Vector filesMatched = matchQuery(queryMessage);
        if(!filesMatched.isEmpty())
          sendQueryHit(queryMessage, filesMatched);
        else
          forwardQueryMessage(queryMessage);
      }
    }
  }

  private Vector matchQuery(QueryMessage queryMessage) {
    Vector filesMatched = new Vector();
    int queryType = queryMessage.getQueryType();
    String fileName;
    FileShared searchFile, foundFile;
    String words[];
    StringBuffer buffer;
    int fileType = -1;

    switch(queryType) {
      case QueryTypes.FULL_QUERY:
        fileName = queryMessage.getFileName();
        searchFile = new FileShared(fileName, queryMessage.getFileType());
        foundFile = this.node.getFileCompletelyMatchingName(searchFile);
        if(foundFile.getFileType() != -1)
          filesMatched.addElement(foundFile);
        break;

      case QueryTypes.PARTIAL_QUERY:
        words = queryMessage.getPartialWords();
        fileType = queryMessage.getFileType();
        filesMatched = this.node.getFilesPartiallyMatchingName(words, fileType);
        break;

      case QueryTypes.MULTIPLE_QUERIES:
        break;
    }
    return filesMatched;
  }

  private void sendQueryHit(QueryMessage queryMessage, Vector filesMatched) {
    int myId = node.getNodeId();
    double time = currentSimulationTime();
    int query_origin = queryMessage.getQueryOrigin();
    int query_sender = queryMessage.getSender();
    QueryHitMessage queryHitMsg = new QueryHitMessage(query_origin, myId, query_sender, time, filesMatched);
    TopologyReader topoReader = (TopologyReader)(node.getTopologyReader());
    Node receiverNode = topoReader.getTopologyInfoForNode(query_sender);
    double delay = topoReader.delayLatencyBetween(myId, query_sender, (MessageConstants.QUERY_HIT_MSG_SIZE_MULTIPLE_MIN)*(Preferences.default_MTU));;
    double receiveTime = time + delay;

    printInfo(queryHitMsg.msgString());
    SendMessageCommand sendCommand = new SendMessageCommand(receiveTime, receiverNode, queryHitMsg);
    send(sendCommand);
  }

  private void forwardQueryMessage(QueryMessage queryMessage) {
    queryMessage.decrementTTL();
    if(!queryMessage.ttlZero()) {
      int query_origin = queryMessage.getQueryOrigin();
      int query_sender = queryMessage.getSender();
      int myId = node.getNodeId();
      double time = currentSimulationTime();
      TopologyReader topoReader = (TopologyReader)(node.getTopologyReader());
      //printCurrentPeers();
      for(int index = 0; index <  myCurrentPeers.size(); index++) {
        int peerId = ((Integer)(myCurrentPeers.elementAt(index))).intValue();
        if(peerId != query_sender) {
          queryMessage.setSender(myId);
          queryMessage.setReceiver(peerId);
          queryMessage.setTimeStamp(time);
          Node receiverNode = topoReader.getTopologyInfoForNode(peerId);
          double delay = topoReader.delayLatencyBetween(myId, peerId, (MessageConstants.QUERY_MSG_SIZE_MULTIPLE_MIN)*(Preferences.default_MTU));
          double receiveTime = time + delay;
          printInfo(queryMessage.msgString());
          SendMessageCommand sendCommand = new SendMessageCommand(receiveTime, receiverNode, queryMessage);
          send(sendCommand);
        }
      }
    }
  }

  private void send(Command sendmsgCommand) {
    Simulator.getInstance().schedule(sendmsgCommand);
  }

  public void recv(Message msg) {
    String str = new String();
    switch(msg.getMessageType()) {
      case MessageConstants.PING:
        str = new String(" received PING from "+msg.getSender());
        break;
      case MessageConstants.PONG:
        str = new String(" received PONG from "+msg.getSender());
        break;
      case MessageConstants.QUERY:
        str = new String(" received QUERY from "+msg.getSender());
        break;
      case MessageConstants.QUERY_HIT:
        str = new String(" received QUERY_HIT from "+msg.getSender());
        break;
      case MessageConstants.HEART_BEAT:
        str = new String(" received HEART_BEAT from "+msg.getSender());
        break;
    }

    printInfo(currentSimulationTime(), str);
    this.msgQueue.addElement(msg);
    run();
  }

  private Vector processReceivedMessages(int msgType) {
    Vector messages = new Vector();
    for(int index = 0; index < this.msgQueue.size(); index++){
      Message msg = (Message)this.msgQueue.elementAt(index);
      if(msg.getMessageType() == msgType)
        messages.addElement(msg);
    }
    this.msgQueue.removeAll(messages);
    return messages;
  }

  private void startTimer(int timerId) {
    //printInfo(currentSimulationTime(), new String("in startTimer()"));
    double futureTime;
    TimeOutCommand timeOutCommand;
    switch(timerId) {
      case NEIGHBOR_DISCOVERY_TIMER:
        futureTime = currentSimulationTime() + Preferences.NEIGHBOR_TIMEOUT;
        timeOutCommand = new TimeOutCommand(futureTime, Preferences.NEIGHBOR_TIMEOUT, this.node, NEIGHBOR_DISCOVERY_TIMER);
        Simulator.getInstance().schedule(timeOutCommand);
        break;

      case HEARTBEAT_TIMER:
        futureTime = currentSimulationTime() + Preferences.HEARTBEAT_TIMEOUT;
        timeOutCommand = new TimeOutCommand(futureTime, Preferences.HEARTBEAT_TIMEOUT, this.node, HEARTBEAT_TIMER);
        Simulator.getInstance().schedule(timeOutCommand);
        break;

    }
  }

  public void timerExpired(int timerId) {
    //printInfo(currentSimulationTime(), new String("in timerExpired()"));
    switch(timerId) {
      case NEIGHBOR_DISCOVERY_TIMER:
        //printInfo(currentSimulationTime(), new String(" NEIGHBOR_DISCOVERY_TIMER expired "));
        Vector pongMessages = processReceivedMessages(MessageConstants.PONG);
        for(int index = 0; index < pongMessages.size(); index++) {
          PongMessage pongMessage = (PongMessage)(pongMessages.elementAt(index));
          int peerId = pongMessage.getSender();
          this.myCurrentPeers.addElement(new Integer(peerId));
        }
        //printCurrentPeers();
        if(!this.myCurrentPeers.isEmpty()) {
          this.isConnected = true;
          startTimer(HEARTBEAT_TIMER);
        }
        break;

      case HEARTBEAT_TIMER:
        //printInfo(currentSimulationTime(), new String(" HEARTBEAT_TIMER expired "));
        // Send HeartBeat Messages ...
        sendHeartBeatMessages();
        // Receive HeartBeat Messages, if any ...
        Vector heartBeatMessages = processReceivedMessages(MessageConstants.HEART_BEAT);
        Vector newNeighbors = new Vector();
        if(!heartBeatMessages.isEmpty()) {
          for(int index = 0; index < heartBeatMessages.size(); index++) {
            HeartBeatMessage heartBeatMessage = (HeartBeatMessage)(heartBeatMessages.elementAt(index));
            int peerId = heartBeatMessage.getSender();
            newNeighbors.addElement(new Integer(peerId));
          }
          this.myCurrentPeers.removeAllElements();
          this.myCurrentPeers.addAll(newNeighbors);
          printCurrentPeers(); // show current set of neighboring peers ...
        }
        startTimer(HEARTBEAT_TIMER); // re-schedule the heartbeat timer ...
        break;
    }
  }

  private void sendHeartBeatMessages() {
    int origin = node.getNodeId();
    double time = currentSimulationTime();
    TopologyReader topoReader = (TopologyReader)(node.getTopologyReader());
    for(int index = 0; index <  myCurrentPeers.size(); index++) {
      int receiver = ((Integer)(myCurrentPeers.elementAt(index))).intValue();
      Node receiverNode = topoReader.getTopologyInfoForNode(receiver);
      double delay = topoReader.delayLatencyBetween(origin, receiver, (MessageConstants.QUERY_MSG_SIZE_MULTIPLE_MIN)*(Preferences.default_MTU));;
      double receiveTime = time + delay;

      // Send HeartBeat Message ...
      HeartBeatMessage heartBeatMessage = new HeartBeatMessage(origin, receiver, time);
      printInfo(heartBeatMessage.msgString());
      SendMessageCommand sendCommand = new SendMessageCommand(receiveTime, receiverNode, heartBeatMessage);
      send(sendCommand);
    }
  }

  public void sendFullQueryMessage(String fileName, String fileExtension, int ttl) {
    int origin = node.getNodeId();
    double time = currentSimulationTime();
    TopologyReader topoReader = (TopologyReader)(node.getTopologyReader());
    for(int index = 0; index <  myCurrentPeers.size(); index++) {
      int receiver = ((Integer)(myCurrentPeers.elementAt(index))).intValue();
      Node receiverNode = topoReader.getTopologyInfoForNode(receiver);
      double delay = topoReader.delayLatencyBetween(origin, receiver, (MessageConstants.QUERY_MSG_SIZE_MULTIPLE_MIN)*(Preferences.default_MTU));;
      double receiveTime = time + delay;

      // Send Full Query Message ...
      QueryMessage fullQueryMessage = new QueryMessage(origin, receiver, time, fileName, fileExtension, ttl);
      printInfo(fullQueryMessage.msgString());
      SendMessageCommand sendCommand = new SendMessageCommand(receiveTime, receiverNode, fullQueryMessage);
      send(sendCommand);
    }
  }

  public void sendPartialQueryMessage(String[] words, String fileExtension, int ttl) {
    int origin = node.getNodeId();
    double time = currentSimulationTime();
    TopologyReader topoReader = (TopologyReader)(node.getTopologyReader());
    //printCurrentPeers();
    for(int index = 0; index <  myCurrentPeers.size(); index++) {
      int receiver = ((Integer)(myCurrentPeers.elementAt(index))).intValue();
      //System.out.println("*********** PartialQuery from node "+origin+" to node "+receiver);
      Node receiverNode = topoReader.getTopologyInfoForNode(receiver);
      double delay = topoReader.delayLatencyBetween(origin, receiver, (MessageConstants.QUERY_MSG_SIZE_MULTIPLE_MIN)*(Preferences.default_MTU));;
      double receiveTime = time + delay;

      // Send Partial Query Message ...
      QueryMessage partialQueryMessage = new QueryMessage(origin, receiver, time, words, fileExtension, ttl);
      printInfo(partialQueryMessage.msgString());
      SendMessageCommand sendCommand = new SendMessageCommand(receiveTime, receiverNode, partialQueryMessage);
      send(sendCommand);
    }
  }

  public void sendMultipleQueriesMessage(int timeInterval, double queryRate, DistributionInfo distInfo, int ttl) {

  }


  public void printCurrentPeers() {
    System.out.print(" Node "+node.getNodeId()+" has "+this.myCurrentPeers.size()+" neighbors and they are");
   for(int i = 0; i < this.myCurrentPeers.size(); i++)  {
     System.out.print(" "+this.myCurrentPeers.elementAt(i).toString()+" ");
   }
   System.out.println();
  }

  private double currentSimulationTime() {
    return Simulator.getInstance().getTime();
  }

  private void printInfo(double time, String str) {
    if(print)
      System.out.println(time+": node "+node.getNodeId()+" "+str);
  }

  private void printInfo(String str) {
     System.out.println(str);
  }
} // End of Protocol Agent Class ...
