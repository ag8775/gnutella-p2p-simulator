package p2psimulator.util;
import p2psimulator.prng.Distribution;
import p2psimulator.util.FormatWriter;
import java.util.Random;
import java.util.StringTokenizer;
import java.io.*;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class Dictionary {
  private String inputfile, dictionaryfile;
  private int numWords;
  private FormatWriter fileout;
  private Random r;
  public Dictionary(String fileName) {
    this.inputfile = fileName;
    openDictionaryFile();
    this.numWords = 0;
    String record;
    r = new Random();
    try
    {
      FileReader fr = new FileReader(this.inputfile);
      BufferedReader br = new BufferedReader(fr);
      record = new String();
      while ((record = br.readLine()) != null)
       parseLine(record);
      fr.close();
      br.close();
    }
    catch (IOException e)
    {
      // catch possible io errors from readLine()
      System.out.println("Uh oh, got an IOException error!");
      e.printStackTrace();
    }
    closeDictionaryFile();
  }

  private void parseLine(String line) {
    String delimiters = new String("\" , . & ? ! ( ) : - / \\");
    StringTokenizer st = new StringTokenizer(line, delimiters);
    String str = new String();
    int index = 0;
    while(st.hasMoreTokens()) {
      str = st.nextToken();
      this.numWords+=1;
      writeDictionaryFile(this.numWords+": "+str+";");
    }
  }

 public int getTotalWords() {
   return this.numWords;
 }
  public String getRandomWord() {
    String str = new String();
    String record;
    int randomNumber = Distribution.getUniformRandom(r, 1, numWords);
    int i = 1;
    try
    {
      FileReader fr = new FileReader(this.dictionaryfile);
      BufferedReader br = new BufferedReader(fr);
      record = new String();
      while (((record = br.readLine()) != null)&&(i <= randomNumber)) {
        if(i == randomNumber)
          str = parseDictionaryRecord(record);
        i++;
      }
      fr.close();
      br.close();
    }
    catch (IOException e)
    {
      // catch possible io errors from readLine()
      System.out.println("Uh oh, got an IOException error!");
      e.printStackTrace();
    }
     return str;
  }

  private String parseDictionaryRecord(String record) {
    // Random Number Scenario File format ...
    // index: String
    // 2: Manish;

    String delimiters = new String(": ;");
    StringTokenizer st = new StringTokenizer(record, delimiters);
    String str = new String();
    int index = 0;
    while(st.hasMoreTokens()) {
      index = Integer.valueOf(st.nextToken()).intValue();
      str = st.nextToken();
    }
    return str;
  }

  private void openDictionaryFile()
  {
    try {
          this.dictionaryfile = new String("./scen/dictionary_words.txt");
          this.fileout = new FormatWriter(new BufferedWriter(new FileWriter(dictionaryfile)), 5);
        }
    catch (IOException ae)
     {
        System.out.println("IO exception thrown: " + ae);
     }
  }

  private void closeDictionaryFile()
  {
    try {
      fileout.close();
    }
    catch (Exception io)
    {
      System.out.println("Error during closing Dictionary file");
    }
  }

  private void writeDictionaryFile(String str)
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