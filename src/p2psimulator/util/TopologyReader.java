package p2psimulator.util;
import p2psimulator.generators.Coordinates;
import p2psimulator.prng.Distribution;
import p2psimulator.util.DistributionTypes;
import p2psimulator.util.DistributionInfo;
import p2psimulator.element.Node;
import p2psimulator.util.Preferences;
import java.io.*;
import java.util.Vector;
import java.util.StringTokenizer;
import java.util.Random;
/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class TopologyReader {
  private String topologyFileName;
  private int numNodes;
  private Node nodes[];
  private Random seed;

  public TopologyReader(String fileName, Node nodes[], Random r) {
   this.topologyFileName = fileName;
   this.numNodes = nodes.length;
   this.nodes = nodes;
   this.seed = r;
  }

  public void initializeParametersForAllNodes() {
    String record;
    try
    {
      FileReader fr = new FileReader(this.topologyFileName);
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
    String delimiters = new String(": , ; { } ( ) \"Mhz\" \"Mbps\" \"MB\" ");
    StringTokenizer st = new StringTokenizer(line, delimiters);
    String str = new String();
    int nodeId = -1;
    int coordX = -1;
    int coordY = -1;
    double cpu = -1;
    double memory = -1;
    double hdisk = -1;
    double bandwidth = -1;
    int numNeighbors = -1;
    Vector neighbors = new Vector();
    // nodeId: (coordX, coordY) numNeighbors {neighbor1, ...., neighborn} {cpu Mhz, mem MB, hdisk MB, bandwidth Mbps};
    while(st.hasMoreTokens()) {
      nodeId = Integer.valueOf(st.nextToken()).intValue();
      coordX = Integer.valueOf(st.nextToken()).intValue();
      coordY = Integer.valueOf(st.nextToken()).intValue();
      this.nodes[nodeId].setNodeCoords(coordX, coordY);
      numNeighbors = Integer.valueOf(st.nextToken()).intValue();
      int index = 0;
      while(index < numNeighbors) {
        neighbors.addElement(Integer.valueOf(st.nextToken()));
        index++;
      }
      cpu = Double.valueOf(st.nextToken()).doubleValue();
      memory = Double.valueOf(st.nextToken()).doubleValue();
      hdisk = Double.valueOf(st.nextToken()).doubleValue();
      bandwidth = Double.valueOf(st.nextToken()).doubleValue();
    }
    this.nodes[nodeId].setCPUValue(cpu);
    this.nodes[nodeId].setMemoryValue(memory);
    this.nodes[nodeId].setHdiskValue(hdisk);
    this.nodes[nodeId].setBWValue(bandwidth);
    this.nodes[nodeId].setNodeNeighbors(neighbors);
    this.nodes[nodeId].setTopologyReader((TopologyReader)this); // a hack to get node instance for Sending/Receiving Messages via Command Interface ...
    this.nodes[nodeId].setRandom(seed);
  }

  public Node getTopologyInfoForNode(int nodeId) {
    return this.nodes[nodeId];
  }

  public double delayLatencyBetween(int fromNode, int toNode, double totalMessageSize) {
    double totaldelay;
    double delayPerHop;
    long numMTUs = Math.round(totalMessageSize/Preferences.default_MTU);
    int fromX = this.nodes[fromNode].getCoordX();
    int fromY = this.nodes[fromNode].getCoordY();
    int toX = this.nodes[toNode].getCoordX();
    int toY = this.nodes[toNode].getCoordY();
    double distance = Math.sqrt((Math.pow((fromX-toX), 2)+Math.pow((fromY-toY), 2))); // in Km
    long avg_distance_per_hop = Math.round(Distribution.getUniformRandom(seed, Preferences.minimum_average_geographical_distance_per_hop, Preferences.minimum_average_geographical_distance_per_hop));
    long numHops = Math.round(distance/avg_distance_per_hop);
    int minAverageLinkDelayPerHop = (int)Math.round((Preferences.minimum_average_link_delay_per_hop)*1000000);
    int maxAverageLinkDelayPerHop = ((int)Math.round((Preferences.maximum_average_link_delay_per_hop)*1000000));
    double avg_link_delay_per_hop = (Distribution.getUniformRandom(seed, minAverageLinkDelayPerHop, maxAverageLinkDelayPerHop)); //in us
    double avg_router_processing_delay_per_hop = (Distribution.getUniformRandom(seed, ((int)Math.round((Preferences.minimum_router_processing_delay_per_hop)*1000000)), ((int)Math.round((Preferences.maximum_router_processing_delay_per_hop)*1000000)))); //in us
    delayPerHop = avg_link_delay_per_hop + avg_router_processing_delay_per_hop;
    totaldelay = (delayPerHop*numMTUs*numHops)/1000000; //in ms
    //System.out.println("Delay Calculation from node "+fromNode+" to node "+toNode+": distance = "+distance+" avg_distance_per_hop = "+avg_distance_per_hop+" numHops = "+numHops+" avg_link_delay_per_hop = "+avg_link_delay_per_hop+" avg_router_processing_delay_per_hop = "+avg_router_processing_delay_per_hop+" totaldelay = "+totaldelay);
    return totaldelay;
  }
}