package p2psimulator.generators;
import p2psimulator.util.FormatWriter;
import p2psimulator.util.Preferences;
import p2psimulator.util.DistributionTypes;
import p2psimulator.prng.Distribution;
import java.util.Random;
import java.util.Vector;
import java.io.*;


/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class TopologyScenarioGenerator {
  private int numNodes;
  private double minBW;  // min BW Mbps
  private double maxBW;  // max BW kbps
  private double minCPU; // min CPU in Mhz
  private double maxCPU; // max CPU in Mhz
  private int minMemory; // min Memory in MB
  private int maxMemory; // max Memory in MB
  private int minDiskSpace; // min H/disk space in MB
  private int maxDiskSpace; // max H/disk space in MB
  private int topologyModel; // Random, Waxman, Barabasi Albert Model, etc ...
  private int bandwidthDistribution; // Random, Exponential, Uniform, Heavytailed ...
  private FormatWriter fileout ;
  private String scenarioFile ;
  private Random r;
  private Coordinates coords[];
  private long maxX, maxY;

  public TopologyScenarioGenerator(int numNodes, Random rn) {
    this.numNodes = numNodes;
    this.r = rn;
    coords = new Coordinates[this.numNodes];
    this.maxX = Preferences.maximum_network_x_dimension;
    this.maxY = Preferences.maximum_network_y_dimension;
    this.minBW = Preferences.minimum_bandwidth;
    this.maxBW = Preferences.maximum_bandwidth;
    this.minCPU = Preferences.minimum_computational_power;
    this.maxCPU = Preferences.maximum_computational_power;
    this.minMemory = Preferences.minimum_memory;
    this.maxMemory = Preferences.maximum_memory;
    this.minDiskSpace = Preferences.minimum_disk_space;
    this.maxDiskSpace = Preferences.maximum_disk_space;
    this.scenarioFile = new String("./scen/topologyScenarioFile.txt");
    openFile(this.scenarioFile);
  }

  public String topologyScenarioFile() {
    return this.scenarioFile;
  }

  public void generateTopologyScenario() {
    long bandwidth;
    long cpuValue;
    long memory_size;
    long disk_space;
    long average_distance_per_hop;
    long coordX, coordY;

    for(int nodeId = 0; nodeId < this.numNodes; nodeId++) {
      coords[nodeId] = new Coordinates(nodeId, Math.round(Distribution.getUniformRandom(r)*this.maxX), Math.round(Distribution.getUniformRandom(r)*this.maxY));
    }

    for(int nodeId = 0; nodeId < this.numNodes; nodeId++) {
      bandwidth = (long)Math.round(Distribution.getUniformRandom(r, minBW, maxBW)*100)/100;
      cpuValue = (long)Math.round(Distribution.getUniformRandom(r, minCPU, maxCPU));
      memory_size = (long)Math.round(Distribution.getUniformRandom(r, minMemory, maxMemory));
      disk_space = (long)Math.round(Distribution.getUniformRandom(r, minDiskSpace, maxDiskSpace));
      coordX = coords[nodeId].getX();
      coordY = coords[nodeId].getY();
      average_distance_per_hop = Math.round(Distribution.getUniformRandom(r, Preferences.minimum_average_geographical_distance_per_hop, Preferences.maximum_average_geographical_distance_per_hop));
      Vector neighbors = new Vector();
      for(int neighId = 0; neighId < this.numNodes;  neighId++) {
        long neighX = coords[neighId].getX();
        long neighY = coords[neighId].getY();
        if(neighId != nodeId) {
          if(Math.sqrt(Math.pow((coordX-neighX), 2) + Math.pow((coordY-neighY), 2)) <= average_distance_per_hop)
            neighbors.addElement(new Integer(neighId));
        }
      }
      writeFile(new String(nodeId+": ("+coords[nodeId].getX()+", "+coords[nodeId].getY()+") "+neighbors.size()+" {"+neighborsToString(neighbors)+"} {"+cpuValue+" Mhz, "+memory_size+" MB, "+disk_space+" MB, "+bandwidth+" Mbps};"));
    }
    closeFile();
  }


  private String neighborsToString(Vector neighbors) {
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

  private void openFile(String fileName)
    {
      try {
            this.fileout = new FormatWriter(new BufferedWriter(new FileWriter(fileName)), 5);
          }
      catch (IOException ae)
       {
          System.out.println("IO exception thrown: " + ae);
       }
    }

    private void closeFile()
    {
      try {
        fileout.close();
      }
      catch (Exception io)
      {
        System.out.println("Error during closing file");
      }
    }

    private void writeFile(String str)
    {
      try
      {
        fileout.println(str);
      }
      catch(Exception ae)
      {
        System.out.println("IO exception Thrown: " + ae);
      }
  }
}
