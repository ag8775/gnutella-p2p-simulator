package p2psimulator.generators;
import p2psimulator.util.FormatWriter;
import p2psimulator.util.FileTypes;
import java.io.*;
/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class TrafficScenarioGenerator {

  private int numNodes;
  private String scenarioFile;
  private double totalSimulationTime;
  private FormatWriter fileout;

  public TrafficScenarioGenerator(int numNodes, double totalSimulationTime) {
    this.numNodes = numNodes;
    this.totalSimulationTime = totalSimulationTime;
  }

  public String trafficScenarioFile() {
    return this.scenarioFile;
  }

  public void generateTrafficScenario() {
    double currentTime = 0;
    while(currentTime <= this.totalSimulationTime) {

    }
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