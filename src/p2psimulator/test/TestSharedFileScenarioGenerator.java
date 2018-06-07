package p2psimulator.test;
import p2psimulator.util.Dictionary;
import p2psimulator.generators.GenerateSharedFilesScenario;
import java.io.File;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class TestSharedFileScenarioGenerator {
  public static void main(String[] args) {
     String dictionaryFile = ((new File("./scen/somefile.txt")).getPath());
     GenerateSharedFilesScenario genSharedFiles = new GenerateSharedFilesScenario(dictionaryFile);
     genSharedFiles.generateSharedFiles();
  }
}