package p2psimulator.command;
import p2psimulator.element.Node;
import p2psimulator.util.QueryTypes;
import p2psimulator.util.DistributionInfo;
import java.util.*;
/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class QueryCommand extends Command{
  private int queryType;
  private Node m_node;
  private String fileName;
  private int ttl;
  private String words[];
  private String fileExtension;
  private int timeInterval;
  private double queryRate;
  private DistributionInfo distInfo;

  public QueryCommand(double time, Node node, String fileName, String fileExtension, int ttl) {
    super("FullQueryCommand", time);
    this.queryType = QueryTypes.FULL_QUERY;
    this.m_node = node;
    this.fileName = fileName;
     this.fileExtension = fileExtension;
    this.ttl = ttl;
  }

  public QueryCommand(double time, Node node, String[] word, String fileExtension, int ttl) {
    super("PartialQueryCommand", time);
    this.queryType = QueryTypes.PARTIAL_QUERY;
    this.m_node = node;
    this.words = word;
    this.fileExtension = new String(fileExtension);
    this.ttl = ttl;
  }

  public QueryCommand(double time, Node node, int timeInterval, double queryRate, DistributionInfo distInfo, int ttl) {
     super("MultipleQueriesCommand", time);
     this.queryType = QueryTypes.MULTIPLE_QUERIES;
     this.m_node = node;
     this.timeInterval = timeInterval;
     this.queryRate = queryRate;
     this.distInfo = distInfo;
     this.ttl = ttl;
  }

   public void execute() {
     switch(this.queryType) {
       case QueryTypes.FULL_QUERY:
         this.m_node.initiateFullQuery(fileName, fileExtension, ttl);
         break;
       case QueryTypes.PARTIAL_QUERY:
         this.m_node.initiatePartialQuery(words, fileExtension, ttl);
         break;
       case QueryTypes.MULTIPLE_QUERIES:
         this.m_node.initiateMultipleQueries(timeInterval, queryRate, distInfo, ttl);
         break;
     }
   }
}