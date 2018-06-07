package p2psimulator.command;
import p2psimulator.command.Command;
import p2psimulator.element.Node;
import p2psimulator.util.BandwidthEventTypes;
import p2psimulator.util.DistributionInfo;
import java.util.*;
/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class NodeBandwidthCommand extends Command {
  private Node m_node;

  public NodeBandwidthCommand(double time, Node node, int bwEventType, double upstreamBW, double downstreamBW, DistributionInfo distInfo) {
    super("BandwidthUpdateCommand", time);
  }
  /**
       Call the update function of the Node.
    */
    public void execute() {
      m_node.update();
    }
}