package p2psimulator.protocol.message;
import p2psimulator.protocol.message.Message;
import p2psimulator.util.QueryTypes;
import p2psimulator.protocol.message.MessageConstants;
import p2psimulator.util.FileTypes;
/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class QueryMessage extends Message {
  private int queryType;
  private int ttl;
  private String fileName;
  private String[] words;
  private String[] fileNames;
  private String fileExtension;
  private int queryOrigin;
  private int fileType;

  public QueryMessage(int origin, int receiver, double timestamp, String fileName, String fileExtension, int ttl) {
    this.queryOrigin = origin;
    this.sender = origin;
    this.receiver = receiver;
    this.timeStamp = timestamp;
    this.msgType = MessageConstants.QUERY;
    this.fileName = fileName;
    this.fileType = FileTypes.getFileType(fileExtension);
    this.ttl = ttl;
    this.queryType = QueryTypes.FULL_QUERY;
  }

  public QueryMessage(int origin, int receiver, double timestamp, String[] words, String fileExtension, int ttl) {
    this.queryOrigin = origin;
    this.sender = origin;
    this.receiver = receiver;
    this.timeStamp = timestamp;
    this.msgType = MessageConstants.QUERY;
    this.words = words;
    this.fileExtension = fileExtension;
    this.fileType = FileTypes.getFileType(fileExtension);
    this.ttl = ttl;
    this.queryType = QueryTypes.PARTIAL_QUERY;
  }

  public QueryMessage(int origin, int receiver, double timestamp, String[] fileNames, int fileType, int ttl) {
   this.queryOrigin = origin;
   this.sender = origin;
   this.receiver = receiver;
   this.timeStamp = timestamp;
   this.msgType = MessageConstants.QUERY;
   this.fileNames = fileNames;
   this.fileType = fileType;
   this.ttl = ttl;
   this.queryType = QueryTypes.MULTIPLE_QUERIES;
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

  public int getQueryOrigin() {
    return this.queryOrigin;
  }

  public int getQueryType() {
    return this.queryType;
  }

  public String getFileName() {
    return this.fileName;
  }

  public String getFileExtension() {
     return this.fileExtension;
  }

  public String[] getPartialWords() {
   return this.words;
  }

  public String[] getMultipleFileNames() {
   return this.fileNames;
  }

  public int getFileType() {
    return this.fileType;
  }

  public int getTTL() {
    return this.ttl;
  }

  public void decrementTTL() {
    this.ttl-=1;
  }

  public boolean ttlZero() {
    return (this.ttl <= 0);
  }

 public String msgString() {
   String str = new String();
   switch(this.queryType) {
     case QueryTypes.FULL_QUERY:
       if(this.queryOrigin == this.sender)
         str = new String("Node "+this.sender+": at time "+this.timeStamp+" sending <FULL_QUERY> to next hop neighbor "+this.receiver);
       else
         str = new String("Node "+this.sender+": at time "+this.timeStamp+" forwarding <FULL_QUERY> for node "+this.queryOrigin+" to next hop neighbor "+this.receiver);
       break;
     case QueryTypes.PARTIAL_QUERY:
       if(this.queryOrigin == this.sender)
        str = new String("Node "+this.sender+": at time "+this.timeStamp+" sending <PARTIAL_QUERY> to next hop neighbor "+this.receiver);
      else
         str = new String("Node "+this.sender+": at time "+this.timeStamp+" forwarding <PARTIAL_QUERY> for node "+this.queryOrigin+" to next hop neighbor "+this.receiver);
       break;
     case QueryTypes.MULTIPLE_QUERIES:
       if(this.queryOrigin == this.sender)
        str = new String("Node "+this.sender+": at time "+this.timeStamp+" sending <MULTIPLE_QUERIES> to next hop neighbor "+this.receiver);
      else
         str = new String("Node "+this.sender+": at time "+this.timeStamp+" forwarding <MULTIPLE_QUERIES> for node "+this.queryOrigin+" to next hop neighbor "+this.receiver);
       break;
   }
   return str;
 }
}