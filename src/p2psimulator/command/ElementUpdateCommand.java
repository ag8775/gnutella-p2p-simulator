package p2psimulator.command;
import p2psimulator.element.Element;
import p2psimulator.Simulator;
/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

/**
   ElementUpdateCommand is a command class that can be used genericly to
   schedule a call to any element's update() function at a specific time.
*/
public class ElementUpdateCommand extends Command {

    private Element m_element;

    public ElementUpdateCommand(Element element,double time) {
      super("ElementUpdate",time);
      m_element=element;
    }

    /**
       Call the update function of the element.
    */
    public void execute() {
      m_element.update();
    }

}
