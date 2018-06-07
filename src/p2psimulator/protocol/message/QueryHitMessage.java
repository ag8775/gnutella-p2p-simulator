package p2psimulator.protocol.message;
import p2psimulator.protocol.message.Message;
import java.util.Vector;
/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class QueryHitMessage extends Message {
  Vector foundFiles;
  int query_origin;

  public QueryHitMessage(int query_origin, int sender, int receiver, double timestamp, Vector foundFiles) {
    this.foundFiles = foundFiles;
    this.query_origin = query_origin;
    this.sender = sender;
    this.receiver = receiver;
    this.timeStamp = timestamp;
    this.msgType = MessageConstants.QUERY_HIT;
  }

  public int getMessageType() {
    return this.msgType;
  }

  public double getTimeStamp() {
    return this.timeStamp;
  }

  public void setTimeStamp(double time) {
    this.timeStamp = time;
  }

  public void setSender(int sender) {
    this.sender = sender;
  }

  public void setQueryOrigin(int origin) {
    this.query_origin = origin;
  }

  public int getSender() {
    return this.sender;
  }

  public void setReceiver(int receiver) {
    this.receiver = receiver;
  }

  public int getReceiver() {
    return this.receiver;
  }

  public int getQueryOrigin() {
    return this.query_origin;
  }


  public String msgString() {
    String str = new String();
    if(this.receiver == this.query_origin)
      str = new String("Node "+this.sender+": at time "+this.timeStamp+" found "+this.foundFiles.size()+" matching files for node "+this.query_origin);
    else
       str = new String("Node "+this.sender+": at time "+this.timeStamp+" found "+this.foundFiles.size()+" matching files for node "+this.query_origin+" and forwarding it via node "+this.receiver);
    return str;
 }

}