package p2psimulator;
import p2psimulator.Simulator;
import p2psimulator.element.*;
import p2psimulator.util.*;
import p2psimulator.command.*;
import p2psimulator.generators.*;
import p2psimulator.protocol.*;

import java.util.Random;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */


/**
   This test program .
*/
public class Test_P2PSimulator {

  public static void main(String args[]) {

    /**
     * Simulation Parameters ...
     */

    Simulator sim;
    GenerateSharedFilesScenario genSharedFiles;
    TopologyScenarioGenerator topoScenGen;
    TopologyReader topoScenReader;
    TrafficScenarioGenerator trafficScenGen;
    TrafficScenarioReader trafficScenReader;
    long seed;
    Random r;
    int numNodes = 10;
    Node nodes[];
    String topologyFileName = new String("./scen/topologyScenarioFile.txt");
    String trafficFileName = new String("./scen/trafficScenarioFile.txt");
    String someSharedLangaugeFile = new String("./scen/somefile.txt");
    String audioSharedScenario;
    String videoSharedScenario;
    String documentSharedScenario;
    String softwareSharedScenario;
    /**
     * Initialize All Simulation Params ...
     */

    System.out.println("Creating "+numNodes+" Node Objects");
    nodes = new Node[numNodes];
    for(int nodeId = 0; nodeId < numNodes; nodeId++) {
      nodes[nodeId] = new Node(nodeId);
    }
    seed = 4096;
    r = new Random(seed);
    genSharedFiles = new GenerateSharedFilesScenario(someSharedLangaugeFile);
    //topoScenGen = new TopologyScenarioGenerator(numNodes, r);
    //trafficScenGen = new TrafficScenarioGenerator();

    /**
     *   Let Generators generate the Simulation Scenarios ...
     */
    genSharedFiles.generateSharedFiles();
    audioSharedScenario = new String(genSharedFiles.getSharedFileScenarioFor(FileTypes.AUDIO));
    videoSharedScenario = new String(genSharedFiles.getSharedFileScenarioFor(FileTypes.VIDEO));
    documentSharedScenario = new String(genSharedFiles.getSharedFileScenarioFor(FileTypes.DOCUMENT));
    softwareSharedScenario = new String(genSharedFiles.getSharedFileScenarioFor(FileTypes.SOFTWARE));
    for(int nodeId = 0; nodeId < numNodes; nodeId++) {
      nodes[nodeId].setSharedFiles(audioSharedScenario, videoSharedScenario, documentSharedScenario, softwareSharedScenario);
    }
    //topoScenGen.generateTopologyScenario();
    //topologyFileName = topoScenGen.topologyScenarioFile();
    //trafficScenGen.generateTrafficScenario();
    //trafficFileName = trafficScenGen.trafficScenarioFile();
    trafficScenReader = new TrafficScenarioReader(trafficFileName, nodes);
    trafficScenReader.loadTrafficPatternOntoSimulator();
    topoScenReader = new TopologyReader(topologyFileName, nodes, r);
    topoScenReader.initializeParametersForAllNodes();
    // Get a simulator
    sim = Simulator.getInstance();

    // Stop the simulator after 1.5 seconds
    //sim.schedule(new StopCommand(4));

    // Start simulating
    sim.run();
  }
}


