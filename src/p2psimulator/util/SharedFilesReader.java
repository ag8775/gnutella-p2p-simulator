package p2psimulator.util;
import p2psimulator.element.FileShared;
import p2psimulator.util.FileTypes;
import p2psimulator.util.Preferences;
import p2psimulator.prng.Distribution;
import p2psimulator.util.DistributionInfo;
import p2psimulator.util.DistributionTypes;
import java.io.*;
import java.util.*;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class SharedFilesReader {
  private Random r;


  public SharedFilesReader(Random seed) {
    this.r = seed;
  }

  public Vector myShareOfFiles(String sharedFileName, int fileType) {
    Vector myShare = new Vector();
    // In FileShared.fileName, just include the fileName, strip the fileExtension ...
    switch(fileType) {
      case FileTypes.AUDIO:
        myShare = generateRandomShare(sharedFileName, fileType, ".mp3") ;
        break;

      case FileTypes.VIDEO:
        myShare = generateRandomShare(sharedFileName, fileType, ".mpeg") ;
        break;

      case FileTypes.DOCUMENT:
        myShare = generateRandomShare(sharedFileName, fileType, ".pdf") ;
        break;

      case FileTypes.SOFTWARE:
        myShare = generateRandomShare(sharedFileName, fileType, ".exe") ;
        break;
    }
    return myShare;
  }

  private Vector generateRandomShare(String sharedFileName, int fileType, String fileExtension) {
    Vector randomFiles = new Vector();
    int maxFilesGenerated = (new Integer(readLineNumber(0, sharedFileName))).intValue();
    int numFilesToChoose = Distribution.getUniformRandom(r, 1, (maxFilesGenerated-1))/4;
    //System.out.println("maxFilesGenerated = "+maxFilesGenerated+" and numFilesToChoose "+numFilesToChoose);
    int count = 0;

    while(count < numFilesToChoose) {
      int randomFileNumber = Distribution.getUniformRandom(r, 1, (maxFilesGenerated-1));
      //System.out.println("Count = "+count+" and Choosing File number "+randomFileNumber);
      randomFiles.addElement(parseLine(readLineNumber(randomFileNumber, sharedFileName), fileType, fileExtension));
      count++;
      //System.out.println("Incrementing Count = "+count);
    }
    return randomFiles;
  }

  private String readLineNumber(int lineNumber, String sharedFileName) {
    String line = new String();
    String record;
    int lineIndex = 0;
    try
    {
      FileReader fr = new FileReader(sharedFileName);
      BufferedReader br = new BufferedReader(fr);
      record = new String();
      while (((record = br.readLine()) != null)&&(lineIndex <= lineNumber)) {
        if(lineIndex == lineNumber)
          line = record;
        lineIndex++;
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
    return line;
  }

  private FileShared parseLine(String line, int fileType, String fileExtension) {
    FileShared fileShared = new FileShared();
    String fileName = new String();
    double fileSize = -1;
    int lineNumber = -1;
    int numWords = -1;
    StringBuffer buffer;// = new StringBuffer();
    String ext;
    String delimiters = new String(": .");
    StringTokenizer st = new StringTokenizer(line, delimiters);
    //System.out.println(" Parsing Line "+line);

    lineNumber = Integer.valueOf(st.nextToken()).intValue();
    numWords = Integer.valueOf(st.nextToken()).intValue();
    int wc = 0;
    String words[] = new String[numWords];
    buffer = new StringBuffer();
    while(wc < numWords) {
      words[wc] = new String(st.nextToken());
      wc++;
    }

    for(wc = 0; wc < (numWords-1); wc++) {
      buffer.append(words[wc]+" ");
    }
    buffer.append(words[numWords-1]);
    fileName = buffer.toString();
    ext = st.nextToken(); // should be file extension without .
    fileSize = Double.valueOf(st.nextToken()).doubleValue();
    //System.out.println(" lineNumber = "+lineNumber+" fileName = "+fileName+" fileSize = "+fileSize);
    st.nextToken(); // this should be MB ...

    fileShared.setFileName(fileName);
    //System.out.println(" done setting fileName ");
    fileShared.setFileId(lineNumber);
    //System.out.println(" done setting lineNumber ");
    fileShared.setFileSize((int)Math.round(fileSize));
    //System.out.println(" done setting fileSize ");
    fileShared.setFileType(fileType);
    //System.out.println(" done setting fileType ");
    return fileShared;
  }

}// End of SharedFilesReader ...