package p2psimulator.protocol.message;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public abstract class Message {

 public int msgType;
 public double timeStamp;
 public int sender;
 public int receiver;

 public abstract int getMessageType() ;
 public abstract double getTimeStamp();
 public abstract void setTimeStamp(double time);
 public abstract void setSender(int sender);
 public abstract int getSender();
 public abstract void setReceiver(int receiver);
 public abstract int getReceiver();

 public abstract String msgString();

} // End of Message Class
