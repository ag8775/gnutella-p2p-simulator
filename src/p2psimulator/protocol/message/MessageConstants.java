package p2psimulator.protocol.message;
import p2psimulator.util.Preferences;
/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class MessageConstants {
 // Bit-flag variant of traditional int enum pattern
 public static final int PING             = 1;  // 1 => Ping Message for neighbor discovery ...
 public static final int PONG             = 2;  // 2 => Pong Message reply for Ping ...
 public static final int QUERY            = 3;  // 3 => Query
 public static final int QUERY_HIT        = 4;  // 4 => Query Hit
 public static final int DATA_TRANSFER    = 5;  // 5 => Data Transfer ...
 public static final int HEART_BEAT       = 6;  // 6 => HeartBeat Message to detect lost neighbors and new Neighbors ...

 // Minimum-Maximum Gnutella Protocol Message Sizes in multiple of MTU's
 public static final int PING_MSG_SIZE_MULTIPLE_MIN = 2;
 public static final int PING_MSG_SIZE_MULTIPLE_MAX = 5;
 public static final int PONG_MSG_SIZE_MULTIPLE_MIN = 2;
 public static final int PONG_MSG_SIZE_MULTIPLE_MAX = 5;
 public static final int QUERY_MSG_SIZE_MULTIPLE_MIN = 5;
 public static final int QUERY_MSG_SIZE_MULTIPLE_MAX = 10;
 public static final int QUERY_HIT_MSG_SIZE_MULTIPLE_MIN = 10;
 public static final int QUERY_HIT_MSG_SIZE_MULTIPLE_MAX = 50;
}