package p2psimulator.util;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class TrafficEventTypes {
  public final static int NODE_INITIALIZE_EVENT = 0;
  public final static int NODE_UP_EVENT = 1;
  public final static int NODE_DOWN_EVENT = 2;
  public final static int NODE_FULL_QUERY_EVENT = 3;
  public final static int NODE_PARTIAL_QUERY_EVENT = 4;
  public final static int NODE_MULTIPLE_QUERY_EVENT = 5;
  public final static int NODE_BANDWIDTH_INCREASE_EVENT = 6;
  public final static int NODE_BANDWIDTH_DECREASE_EVENT = 7;
  public final static int NODE_CAPACITY_INCREASE_EVENT = 8;
  public final static int NODE_CAPACITY_DECREASE_EVENT = 9;
  public final static int NODE_STOP_SIMULATION_EVENT = 10;
}