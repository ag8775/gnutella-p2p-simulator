package p2psimulator.util;
import p2psimulator.element.Node;
import p2psimulator.util.FileTypes;
import p2psimulator.util.QueryTypes;
import p2psimulator.Simulator;
import p2psimulator.prng.Distribution;
import p2psimulator.util.Dictionary;
import p2psimulator.util.Preferences;
import p2psimulator.command.*;
import p2psimulator.util.DistributionTypes;
import p2psimulator.util.DistributionInfo;
import p2psimulator.util.CapacityEventTypes;
import p2psimulator.util.BandwidthEventTypes;
import java.util.Random;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class TrafficScenarioReader {
  private String trafficScenarioFileName;
  private Node nodes[];
  // Define constants for the different types of events scheduled by the
  // traffic generator ...
  /**
   * DOWN a specified gnutella peer
   * time: nodeId -DOWN-;
   */	
  private final static int DOWN = 0;

  /**
   * UP a specified gnutella peer
   * time: nodeId -UP-;
   */
  private final static int UP = 1;

  /**
   * Initialize ALL nodes ...
   * time: numNodes -INITIALIZE-;
   *
   * numNodes is redundant here :-)
   */
  private final static int INITIALIZE = 2;

  /**
   * Initiate Full Query with TTL-limited flooding
   * time: nodeId -INITIATE_FULL_QUERY- nWords {word1, word2, ..., wordn} fileType (TTL);
   */
  private final static int INITIATE_FULL_QUERY = 3;

  /**
   *  Initiate Partial Query with n words and TTL-limited flooding
   * time: nodeId -INITIATE_PARTIAL_QUERY- nWords {word1, word2, ..., wordn} fileType (TTL);
   */
  private final static int INITIATE_PARTIAL_QUERY = 4;

  /**
   * Intiate Multiple Queries with a specified Query Rate ...
   * time: nodeId -INITIATE_MULTIPLE_QUERIES- nParamaters {timeInterval, queryRate, distributionType, distributionParameters, ... } (TTL);
   */
  private final static int INITIATE_MULTIPLE_QUERIES = 5;

  /**
   * Reduce Node's Computational Capabilities
   * time: nodeId -REDUCE_NODE_CAPACITY- nParams {cpu Ghz, memory MB, hdisk GB};
   */
  private final static int REDUCE_NODE_CAPACITY = 6;

  /**
   * Increase Node's Computational Capabilities
   * time: nodeId -INCREASE_NODE_CAPACITY- nParams {cpu Ghz, memory MB, hdisk GB};
   */
  private final static int INCREASE_NODE_CAPACITY = 7;

  /**
   * Reduce Node's upstream/downstream bandwidth
   * time: nodeId -REDUCE_NODE_BW- (upstream_bw Mbps, downstream_bw Mbps) nParams {distributionType, distributionParameters, ...};
   */
  private final static int REDUCE_NODE_BW = 8;

  /**
   * Increase Node's upstream/downstream bandwidth
   * time: nodeId -INCREASE_NODE_BW- (upstream_bw Mbps, downstream_bw Mbps) nParams {distributionType, distributionParameters, ...};
   */
  private final static int INCREASE_NODE_BW = 9;

  /**
   * Drop some/all packets at some node for some time interval ...
   * time: nodeId -DROP_PACKETS- timeInterval;
   *          OR
   * time: nodeId -DROP_PACKETS- timeInterval nParams {distributionType, distributionParameters, ...};
   *
   */
  private final static int DROP_PACKETS = 10;
  //          :
  //          :
  //          :

  /**
   * time: nodeNum -STOP_SIMULATION-;
   *
   * nodeNum is redundant here :-)
   */
  private final static int STOP_SIMULATION = 1111;

  public TrafficScenarioReader(String trafficScenarioFileName, Node nodes[]) {
    this.trafficScenarioFileName = trafficScenarioFileName;
    this.nodes = nodes;
  }

  public void loadTrafficPatternOntoSimulator()
  {
    String record;
    try
    {
      FileReader fr = new FileReader(this.trafficScenarioFileName);
      BufferedReader br = new BufferedReader(fr);
      record = new String();
      while ((record = br.readLine()) != null)
       parseLine(record);
      fr.close();
      br.close();
    }
    catch (IOException e)
    {
      // catch possible io errors from readLine()
      System.out.println("Uh oh, got an IOException error!");
      e.printStackTrace();
    }
  }

  private void parseLine(String line) {
    String delimiters = new String(": , ; { } ( ) \"Mhz\" \"Ghz\" \"Mbps\" \"GB\" \"MB\" \"s\" ");
    StringTokenizer st = new StringTokenizer(line, delimiters);
    String str = new String();
    double time = -1;
    int nodeId = -1;
    int eventType = -1;
    int numTokens = 0;
    while((st.hasMoreTokens())&&(numTokens < 3)) {
      time = Double.valueOf(st.nextToken()).doubleValue();
      nodeId = Integer.valueOf(st.nextToken()).intValue();
      String commandDelimiters = new String("- -");
      eventType = getEventId(st.nextToken(commandDelimiters));
      numTokens+=3;
    }

    switch(eventType) {
      case UP:
        parse_Node_UP_EVENT(time, nodeId);
        break;
      case DOWN:
        parse_Node_DOWN_EVENT(time, nodeId);
        break;
      case INITIALIZE:
        parse_Initialize_EVENT(time);
        break;
      case INITIATE_FULL_QUERY:
        parse_Full_Query_EVENT(time, nodeId, st);
        break;
      case INITIATE_PARTIAL_QUERY:
        parse_Partial_Query_EVENT(time, nodeId, st);
        break;
      case INITIATE_MULTIPLE_QUERIES:
        parse_Multiple_Query_Event(time, nodeId, st);
        break;
      case REDUCE_NODE_CAPACITY:
        parse_Reduce_Capacity_Event(time, nodeId, st);
        break;
      case INCREASE_NODE_CAPACITY:
        parse_Increase_Capacity_Event(time, nodeId, st);
        break;
      case REDUCE_NODE_BW:
        parse_Reduce_BW_Event(time, nodeId, st);
        break;
      case INCREASE_NODE_BW:
        parse_Increase_BW_Event(time, nodeId, st);
        break;
      case DROP_PACKETS:
        parse_Drop_Packets_Event(time, nodeId, st);
        break;
      case STOP_SIMULATION:
        parse_Stop_Simulation_Event(time); // nodeId is irrelevant here :-)
        break;
      default:
        break;
    }
  }

  private int getEventId(String eventString) {
    int eventId = -1;

    if(eventString.compareTo("UP")==0)
      eventId = UP;

    if(eventString.compareTo("DOWN")==0)
      eventId = UP;

    if(eventString.compareTo("INITIALIZE")==0)
      eventId = INITIALIZE;

    if(eventString.compareTo("INITIATE_FULL_QUERY")==0)
      eventId = INITIATE_FULL_QUERY;

    if(eventString.compareTo("INITIATE_PARTIAL_QUERY")==0)
      eventId = INITIATE_PARTIAL_QUERY;

    if(eventString.compareTo("INITIATE_MULTIPLE_QUERIES")==0)
      eventId = INITIATE_MULTIPLE_QUERIES;

    if(eventString.compareTo("REDUCE_NODE_CAPACITY")==0)
      eventId = REDUCE_NODE_CAPACITY;

    if(eventString.compareTo("INCREASE_NODE_CAPACITY")==0)
      eventId = INCREASE_NODE_CAPACITY;

    if(eventString.compareTo("REDUCE_NODE_BW")==0)
     eventId = REDUCE_NODE_BW;

    if(eventString.compareTo("INCREASE_NODE_BW")==0)
         eventId = INCREASE_NODE_BW;

    if(eventString.compareTo("DROP_PACKETS")==0)
         eventId = DROP_PACKETS;

    if(eventString.compareTo("STOP_SIMULATION")==0)
         eventId = STOP_SIMULATION;

    return eventId;
  }

  private void parse_Initialize_EVENT(double time) {
    Simulator.getInstance().schedule(new InitializeCommand(time, nodes));
  }

  private void parse_Node_UP_EVENT(double time, int nodeId) {
    Simulator.getInstance().schedule(new NodeStateCommand(time, nodes[nodeId], UP));
  }

  private void parse_Node_DOWN_EVENT(double time, int nodeId) {
    Simulator.getInstance().schedule(new NodeStateCommand(time, nodes[nodeId], DOWN));
  }

  private void parse_Full_Query_EVENT(double time, int nodeId, StringTokenizer st) {
    int nWords = Integer.valueOf(st.nextToken()).intValue();
    StringBuffer buffer = new StringBuffer();
    String fileExtension;
    int ttl;
    int index = 0;
    while(index < (nWords-1)) {
      buffer.append(st.nextToken()+" ");
      index++;
    }
    buffer.append(st.nextToken());
    fileExtension = new String(st.nextToken());
    //buffer.append(fileExtension);
    ttl = Integer.valueOf(st.nextToken("( )")).intValue();
    Simulator.getInstance().schedule(new QueryCommand(time, nodes[nodeId], buffer.toString(), fileExtension, ttl));
  }

  private void parse_Partial_Query_EVENT(double time, int nodeId, StringTokenizer st) {
    int nWords = Integer.valueOf(st.nextToken()).intValue();

    String words[] = new String[nWords];
    for(int i = 0; i < nWords; i++)
      words[i] = new String();
    String fileExtension;
    int ttl;
    int index = 0;
    String delimiters = new String(" { ,  } ");
    while(index < nWords) {
      words[index] = st.nextToken(delimiters);
      index++;
    }
    fileExtension = new String(st.nextToken());
    ttl = Integer.valueOf(st.nextToken(" ( ) ")).intValue();
    Simulator.getInstance().schedule(new QueryCommand(time, nodes[nodeId], words, fileExtension, ttl));
  }

  private void parse_Multiple_Query_Event(double time, int nodeId, StringTokenizer st) {
    int nParams = Integer.valueOf(st.nextToken()).intValue();
    int timeInterval = -1;
    double queryRate = -1;
    int distributionType = -1;
    Vector distributionParamaters = new Vector();
    DistributionInfo distInfo = new DistributionInfo();
    int ttl;
    int index = 0;
    timeInterval = Integer.valueOf(st.nextToken()).intValue(); // index = 0;
    queryRate = Double.valueOf(st.nextToken()).doubleValue();  // index = 1;
    distributionType = Integer.valueOf(st.nextToken()).intValue(); // index = 2;
    index = 2;
    while(index < nParams) {
      distributionParamaters.addElement(st.nextToken());
      index++;
    }
    distInfo.setDistributionType(distributionType);
    distInfo.setDistributionParameters(distributionParamaters);
    ttl = Integer.valueOf(st.nextToken("( )")).intValue();
    Simulator.getInstance().schedule(new QueryCommand(time, nodes[nodeId], timeInterval, queryRate, distInfo, ttl));
  }

  private void parse_Reduce_Capacity_Event(double time, int nodeId, StringTokenizer st) {
    int nParams = Integer.valueOf(st.nextToken()).intValue();
    double deltaCPU = -1;
    double deltaMem = -1;
    double deltaHdisk =  -1;
    int index = 1;
    int capacityEventType = CapacityEventTypes.REDUCE_CAPACITY;
    deltaCPU = Double.valueOf(st.nextToken()).doubleValue();
    index++;
    deltaMem = Double.valueOf(st.nextToken()).doubleValue();
    index++;
    deltaHdisk = Double.valueOf(st.nextToken()).doubleValue();
    index++; // index should be equal to nParams ? YES
    Simulator.getInstance().schedule(new NodeCapacityCommand(time, nodes[nodeId], capacityEventType, deltaCPU, deltaMem, deltaHdisk));
  }

  private void parse_Increase_Capacity_Event(double time, int nodeId, StringTokenizer st) {
    int nParams = Integer.valueOf(st.nextToken()).intValue();
    double deltaCPU = -1;
    double deltaMem = -1;
    double deltaHdisk =  -1;
    int index = 1;
    int capacityEventType = CapacityEventTypes.INCREASE_CAPACITY;
    deltaCPU = Double.valueOf(st.nextToken()).doubleValue();
    index++;
    deltaMem = Double.valueOf(st.nextToken()).doubleValue();
    index++;
    deltaHdisk = Double.valueOf(st.nextToken()).doubleValue();
    index++; // index should be equal to nParams ? YES
    Simulator.getInstance().schedule(new NodeCapacityCommand(time, nodes[nodeId], capacityEventType, deltaCPU, deltaMem, deltaHdisk));
  }

  private void parse_Reduce_BW_Event(double time, int nodeId, StringTokenizer st) {
    int bwEventType = BandwidthEventTypes.REDUCE_BANDWIDTH;
    double upstreamBW = Double.valueOf(st.nextToken()).doubleValue();
    double downstreamBW = Double.valueOf(st.nextToken()).doubleValue();
    int nParams = Integer.valueOf(st.nextToken()).intValue();
    Vector distributionParamaters = new Vector();
    DistributionInfo distInfo = new DistributionInfo();
    int index = 0;
    int distributionType = Integer.valueOf(st.nextToken()).intValue();
    index++;
    while(index < nParams) {
     distributionParamaters.addElement(st.nextToken());
     index++;
    }
    distInfo.setDistributionType(distributionType);
    distInfo.setDistributionParameters(distributionParamaters);
    Simulator.getInstance().schedule(new NodeBandwidthCommand(time, nodes[nodeId], bwEventType, upstreamBW, downstreamBW, distInfo));
  }

  private void parse_Increase_BW_Event(double time, int nodeId, StringTokenizer st) {
    int bwEventType = BandwidthEventTypes.INCREASE_BANDWIDTH;
    double upstreamBW = Double.valueOf(st.nextToken()).doubleValue();
    double downstreamBW = Double.valueOf(st.nextToken()).doubleValue();
    int nParams = Integer.valueOf(st.nextToken()).intValue();
    Vector distributionParamaters = new Vector();
    DistributionInfo distInfo = new DistributionInfo();
    int index = 0;
    int distributionType = Integer.valueOf(st.nextToken()).intValue();
    index++;
    while(index < nParams) {
     distributionParamaters.addElement(st.nextToken());
     index++;
    }
    distInfo.setDistributionType(distributionType);
    distInfo.setDistributionParameters(distributionParamaters);
    Simulator.getInstance().schedule(new NodeBandwidthCommand(time, nodes[nodeId], bwEventType, upstreamBW, downstreamBW, distInfo));
  }

  private void parse_Drop_Packets_Event(double time, int nodeId, StringTokenizer st) {
    // Some Other time :-( Future Work :-)
  }

  private void parse_Stop_Simulation_Event(double time) {
    Simulator.getInstance().schedule(new StopCommand(time, nodes));
  }
}