package p2psimulator.command;
import p2psimulator.command.Command;
import p2psimulator.Simulator;
import p2psimulator.element.Node;
import p2psimulator.util.Status;
import p2psimulator.protocol.message.*;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

/**
   SendMessageCommand can be used send message from one node i.e. the source to
   another node i.e. the destination.
*/
public class SendMessageCommand extends Command {
    private Message message;
    private Node receiverNode;

    /**
       @param state either Status.UP or Status.DOWN, as defined in
       p2psimulator.util.Status.
    */
    public SendMessageCommand(double receiveTime, Node receiverNode, Message msg) {
      super("NodeState",receiveTime);
      this.message = msg;
      this.receiverNode = receiverNode;
    }

    public void execute() {
     receiverNode.receiveMessage(message);
    }
}
