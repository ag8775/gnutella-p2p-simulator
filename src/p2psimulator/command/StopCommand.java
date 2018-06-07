package p2psimulator.command;
import p2psimulator.command.Command;
import p2psimulator.Simulator;
import p2psimulator.element.*;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

/**
   A StopCommand can be schedule at any time to forcefully interrupt the
   simulator. Some simulations can go on forever so this is quite useful.
*/
public class StopCommand extends Command {
    Node nodes[];
    public StopCommand(double time, Node nodes[]) {
      super("Stop",time);
      this.nodes = nodes;
    }

    /**
       Just set the finished flag in the simulator.
    */
    public void execute() {
      for (int nodeId = 0; nodeId < nodes.length; nodeId++)
        nodes[nodeId].setFinished();
      Simulator.getInstance().setFinished();
    }

}
