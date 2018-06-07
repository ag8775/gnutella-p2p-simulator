package p2psimulator.command;
import p2psimulator.command.Command;
import p2psimulator.element.Node;
/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class InitializeCommand extends Command {

  private Node nodes[];

  public InitializeCommand(double time, Node nodes[]) {
    super("Initialize", time);
    this.nodes = nodes;
  }

   public void execute() {
     for (int nodeId = 0; nodeId < nodes.length; nodeId++) {
       nodes[nodeId].initialize();
     }
   }
}