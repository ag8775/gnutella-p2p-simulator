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

public class TimeOutCommand extends Command {
  private Node node;
  private int timerId;
  private double time_wait;

  public TimeOutCommand(double futureTime, double time_wait, Node node, int timerId) {
    super("TimeOut",futureTime);
    this.time_wait = time_wait;
    this.node = node;
    this.timerId = timerId;
  }

  public void execute() {
    this.node.timerExpired(this.timerId);
  }
}