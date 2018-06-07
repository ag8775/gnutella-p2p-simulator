package p2psimulator.command;
import p2psimulator.command.Command;
import p2psimulator.Simulator;
import p2psimulator.element.Node;
import p2psimulator.util.Status;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

/**
   NodeStateCommand can be used to schedule a change to the state of a Node
   at any time. Nodes can be taken down or brought up like this.
*/
public class NodeStateCommand extends Command {

    private Node m_node;
    private int m_state;

    /**
       @param state either Status.UP or Status.DOWN, as defined in
       p2psimulator.util.Status.
    */
    public NodeStateCommand(double time, Node node, int state) {
      super("NodeState",time);
      m_node=node;
      m_state=state;
    }

    public void execute() {
      m_node.setStatus(m_state);
    }

}

