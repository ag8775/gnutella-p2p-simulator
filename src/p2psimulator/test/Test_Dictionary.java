package p2psimulator.test;
import p2psimulator.util.Dictionary;
import p2psimulator.prng.Distribution;
import java.util.Random;
import java.io.File;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class Test_Dictionary {

  public static void main(String[] args) {
    //Dictionary dict = new Dictionary("D:/MyResearch/CurrentResearch/Coding/simulation/P2PSimulator/scen/somefile.txt");
    Dictionary dict = new Dictionary((new File("./scen/somefile.txt")).getPath());
    System.out.println("Random Word  = \""+dict.getRandomWord()+"\" total number of words = "+dict.getTotalWords());
    int minWords = 2;
    int maxWords = 8;
    Random r = new Random();
    int numWords = Distribution.getUniformRandom(r, minWords, maxWords);
    StringBuffer buffer = new StringBuffer();
    for(int i = 0; i < (numWords-1); i++) {
      buffer.append(new String(dict.getRandomWord()+" "));
    }
    buffer.append(new String(dict.getRandomWord()+".mp3"));
    System.out.println("With Numwords = "+numWords+" FileName = \""+buffer.toString()+"\"");
  }
}