package p2psimulator.command;
import p2psimulator.command.Command;
import p2psimulator.util.CapacityEventTypes;
import p2psimulator.element.Node;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class NodeCapacityCommand extends Command {
  private Node m_node;
  private int capacityEventType;

  public NodeCapacityCommand(double time, Node node, int capacityEventType, double deltaCPU, double deltaMem, double deltaHdisk) {
    super("CapacityUpdateCommand", time);
  }

  /**
       Call the update function of the Node.
    */
    public void execute() {
      m_node.update();
    }
}