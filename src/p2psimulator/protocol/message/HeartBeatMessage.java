package p2psimulator.protocol.message;
import p2psimulator.protocol.message.Message;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class HeartBeatMessage extends Message {

  public HeartBeatMessage(int sender, int receiver, double timestamp) {
   this.sender = sender;
   this.receiver = receiver;
   this.timeStamp = timestamp;
   this.msgType = MessageConstants.HEART_BEAT;
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

 public int getSender() {
   return this.sender;
 }

 public void setReceiver(int receiver) {
   this.receiver = receiver;
 }

 public int getReceiver() {
   return this.receiver;
 }

 public String msgString() {
  return (new String("Node "+this.sender+": at time "+this.timeStamp+" sending <HEARTBEAT> to "+this.receiver));
 }
}