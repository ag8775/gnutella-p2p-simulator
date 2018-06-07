package p2psimulator.element;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

/**
   Element is the abstract superclass of all the static elements in the
   simulator, such as Nodes, Links, etc.
*/
public abstract class Element {

    /**
       Ask the element to update itself.
    */
    public abstract void update();


    /**
       Dump some descriptive information about the element for debugging
       purposes. This will most of the time display information about contained
       elements as well.
    */
    public abstract void dump();


}
