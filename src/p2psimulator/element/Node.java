package p2psimulator.element;
import p2psimulator.protocol.ProtocolAgent;
import p2psimulator.util.SharedFileDatabase;
import p2psimulator.element.FileShared;
import p2psimulator.util.TopologyReader;
import p2psimulator.util.DistributionInfo;
import p2psimulator.util.Status;
import p2psimulator.util.FileTypes;
import p2psimulator.util.SharedFilesReader;
import p2psimulator.Simulator;
import p2psimulator.protocol.message.*;
import p2psimulator.util.Preferences;
import java.util.Random;
import java.util.Vector;
import java.util.Enumeration;
/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

/**
   Node implements a generic network node, i.e. your favourite computer or
   CISCO's favourite product, a router.
*/
public class Node extends Element {
    private String nodeName;
    private int nodeStatus; // The current status of the Node, either Status.UP or Status.DOWN.
    private int nodeId;
    private int coordX;
    private int coordY;
    private double bandwidth; //Mbps
    private double diskSpace; // in MB
    private double computationalPower; // in Mhz
    private double memory; //in MB
    private long currentCapacity;
    private SharedFileDatabase sharedFilesDatabase;
    private boolean simulationRunning;
    private TopologyReader topoReader;
    private SharedFilesReader sharedFilesReader;
    private Vector neighbors;
    private ProtocolAgent gnutellaAgent;
    private Random r;
    private String sharedAudioFile;
    private String sharedVideoFile;
    private String sharedDocumentFile;
    private String sharedSoftwareFile;

    public Node(int id) {
      this.nodeId = id;
      this.nodeName = (new Integer(id)).toString();
      this.neighbors = new Vector();
      this.simulationRunning = true;
    }

    public void createSharedFileDatabase(Vector musicFiles, Vector videoFiles, Vector documentFiles, Vector softwareFiles) {
      this.sharedFilesDatabase = new SharedFileDatabase(musicFiles, videoFiles, documentFiles, softwareFiles);
    }

    public Vector getFilesMatchingWord(String word) {
      return this.sharedFilesDatabase.matchWord(word);
    }

    public Vector getFilesMatchingWords(String words[]) {
     return this.sharedFilesDatabase.matchWords(words);
   }

   public Vector getFilesPartiallyMatchingName(String[] words, int fileType) {
     return this.sharedFilesDatabase.matchPartialFileName(words, fileType);
   }

   public FileShared getFileCompletelyMatchingName(FileShared searchFile) {
     return this.sharedFilesDatabase.matchCompleteFileName(searchFile);
   }

   public Vector getFilesCompletelyMatchingNames(FileShared searchFile[]) {
     return this.sharedFilesDatabase.matchFiles(searchFile);
   }

    public void setNodeId(int id) {
      this.nodeId = id;
      this.nodeName = (new Integer(id)).toString();
    }

    public void setNodeCoords(int x, int y) {
      this.coordX = x;
      this.coordY = y;
    }

    public void setNodeNeighbors(Vector neighborNodes) {
     for(int i = 0; i < neighborNodes.size(); i++)
       neighbors.addElement(neighborNodes.elementAt(i));
    }

    public void setCPUValue(double cpu) {
      this.computationalPower = cpu;
    }

    public void setMemoryValue(double memory) {
     this.memory = memory;
    }

    public void setHdiskValue(double hdisk) {
     this.diskSpace = hdisk;
    }

    public void setBWValue(double bw) {
     this.bandwidth = bw;
    }

    public void setTopologyReader(TopologyReader topoReader) {
      this.topoReader = topoReader;
    }

    public void setRandom(Random rand) {
      this.r = rand;
    }

    public void setSharedFiles(String sharedAudioFile, String sharedVideoFile, String sharedDocumentFile, String sharedSoftwareFile) {
      this.sharedAudioFile = sharedAudioFile;
      this.sharedVideoFile = sharedVideoFile;
      this.sharedDocumentFile = sharedDocumentFile;
      this.sharedSoftwareFile = sharedSoftwareFile;
    }

    public int getNodeId() {
     return this.nodeId;
   }

   public int getCoordX() {
     return this.coordX;
   }

   public int getCoordY() {
    return this.coordY;
   }

   public Vector getNodeNeighbors() {
    return neighbors;
   }

   public double getCPUValue() {
     return this.computationalPower;
   }

   public double getMemoryValue() {
    return this.memory;
   }

   public double getHdiskValue() {
    return this.diskSpace;
   }

   public double getBWValue() {
    return this.bandwidth;
  }

  public long getCurrentCapacity() {
    return this.currentCapacity;
  }

  public TopologyReader getTopologyReader() {
     return this.topoReader;
  }

  public Random getRandom() {
    return this.r;
  }

  /**
   Return the status of this Node, either 'up' or 'down'. Returns one of
   the constans provided in p2psimulator.util.Status.
   @return Status.UP or Status.DOWN.
   */
  public int getStatus() {
    return nodeStatus;
  }

    public void update() {

    }

    public void dump() {
      System.out.println("Node "+this.nodeId+" ("+this.coordX+", "+this.coordY+") :");
      System.out.println("Hardware Info "+this.getCPUValue()+" Mhz "+this.getMemoryValue()+" MB "+this.getHdiskValue()+" MB "+this.getBWValue()+" Mbps ");
      System.out.println(" Current Capacity Value "+this.currentCapacity);
      System.out.println("Neighbors Info "+vtoString(this.neighbors));
    }

    private String vtoString(Vector neighbors) {
      String str = new String();
      int size = neighbors.size();
      if(size != 0) {
        for (int i = 0; i < (size - 1) ; i++) {
          Integer neighObject = (Integer) neighbors.elementAt(i);
          str += neighObject.toString()+ ", " ;
        }
        Integer neighObject = (Integer) neighbors.elementAt(size-1);
        str += neighObject.toString()  ;
      }
      return str;
    }

    public void updateNodeCapacity() {


    }

    private void printInfo(double time, String str) {
       System.out.println(time+": node "+this.nodeId+" "+str);
    }
    /**
     * ------------------------- EVENT DRIVEN METHODS -----------------------
     */

    /**
       Set the status of this Node, either 'up' or 'down'. Use the integer
       constants provided in p2psimulator.util.Status.
       @param status the new status of the link
    */
    public void setStatus(int status) {
      this.nodeStatus=status;
      if(status == Status.UP)
        printInfo(Simulator.getInstance().getTime(), (new String("is UP")));
      else
        printInfo(Simulator.getInstance().getTime(), (new String("is DOWN")));
      gnutellaAgent.run();
    }

    public boolean continueSimulation() {
      return this.simulationRunning;
    }

    public void initialize() {
      System.out.println(Simulator.getInstance().getTime()+" seconds: Node "+this.nodeId+" initializing !!!");
      System.out.println(new String(this.nodeId+": ("+this.coordX+", "+this.coordY+") "+this.neighbors.size()+" {"+vtoString(this.neighbors)+"} {"+this.computationalPower+" Mhz, "+this.memory+" MB, "+this.diskSpace+" MB, "+this.bandwidth+" Mbps};"));
      // Initialize Gnutella Protocol Agent ...
      gnutellaAgent = new ProtocolAgent((Node)this, (new String("Gnutella")));
      // Need to create the shared Files Database ...
      sharedFilesReader = new SharedFilesReader(this.r);
      createSharedFileDatabase(sharedFilesReader.myShareOfFiles(this.sharedAudioFile, FileTypes.AUDIO), sharedFilesReader.myShareOfFiles(this.sharedVideoFile, FileTypes.VIDEO),  sharedFilesReader.myShareOfFiles(this.sharedDocumentFile, FileTypes.DOCUMENT), sharedFilesReader.myShareOfFiles(this.sharedSoftwareFile, FileTypes.SOFTWARE));
    }

    public void receiveMessage(Message msg) {
      gnutellaAgent.recv(msg);
    }

    public void setFinished() {
      System.out.println(Simulator.getInstance().getTime()+" seconds: Node "+this.nodeId+" finishing !!!");
      this.simulationRunning = false;
    }

    public void timerExpired(int timerId) {
      this.gnutellaAgent.timerExpired(timerId);
      this.gnutellaAgent.run();
    }

    public void initiateFullQuery(String fileName, String fileExtension, int ttl) {
      this.gnutellaAgent.sendFullQueryMessage(fileName, fileExtension, ttl);
    }

    public void initiatePartialQuery(String[] words, String fileExtension, int ttl) {
      this.gnutellaAgent.sendPartialQueryMessage(words, fileExtension, ttl);
    }

    public void initiateMultipleQueries(int timeInterval, double queryRate, DistributionInfo distInfo, int ttl) {
      this.gnutellaAgent.sendMultipleQueriesMessage(timeInterval, queryRate, distInfo, ttl);
    }
}
