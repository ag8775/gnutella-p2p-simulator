package p2psimulator.generators;
import p2psimulator.prng.Distribution;
import p2psimulator.util.FileTypes;
import p2psimulator.util.FormatWriter;
import p2psimulator.util.Dictionary;
import p2psimulator.element.FileShared;
import p2psimulator.util.Preferences;
import java.io.*;
import java.util.Random;
/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class GenerateSharedFilesScenario {
  private String musicfile, videofile, documentfile, softwarefile, dictfile;
  private FormatWriter fileout;
  private Dictionary dict;
  private int minWords, maxWords;
  private int minimumFiles, maximumFiles;
  private double minAudioSize, maxAudioSize, minVideoSize, maxVideoSize,
    minDocumentSize, maxDocumentSize, minSoftwareSize, maxSoftwareSize;

  public GenerateSharedFilesScenario(String dictfile) {
    this.dictfile = dictfile;
    this.dict = new Dictionary(this.dictfile);
    this.minWords = Preferences.minimum_words;
    this.maxWords = Preferences.maximum_words;
    this.minimumFiles = Preferences.minimum_files;
    this.maximumFiles = Preferences.maximum_files;
    this.minAudioSize = Preferences.minimum_audio_file_size;
    this.maxAudioSize = Preferences.maximum_audio_file_size;
    this.minVideoSize = Preferences.minimum_video_file_size;
    this.maxVideoSize = Preferences.maximum_video_file_size;
    this.minDocumentSize = Preferences.minimum_document_file_size;
    this.maxDocumentSize = Preferences.maximum_document_file_size;
    this.minSoftwareSize = Preferences.minimum_software_file_size;
    this.maxSoftwareSize = Preferences.maximum_software_file_size;
  }

  public void generateSharedFiles() {
    generateFileScenario(FileTypes.AUDIO);
    generateFileScenario(FileTypes.VIDEO);
    generateFileScenario(FileTypes.DOCUMENT);
    generateFileScenario(FileTypes.SOFTWARE);
  }

  public String getSharedFileScenarioFor(int fileType) {
    String fileName = new String();

    switch(fileType) {
     case FileTypes.AUDIO:
       fileName = (new File("./scen/audioFiles.txt")).getPath();
       break;

     case FileTypes.VIDEO:
       fileName = (new File("./scen/videoFiles.txt")).getPath();
        break;

     case FileTypes.DOCUMENT:
       fileName = (new File("./scen/documentFiles.txt")).getPath();
       break;

     case FileTypes.SOFTWARE:
       fileName = (new File("./scen/softwareFiles.txt")).getPath();
        break;

     default:
       break;
    }
    return fileName;
  }

  private void generateFileScenario(int fileType) {
    double minFileSize = 0, maxFileSize = 0, fileSize;
    Random r = new Random();
    String fileName = new String();
    String fileExtension = new String();
    int numFilesToGenerate = Distribution.getUniformRandom(r, this.minimumFiles, this.maximumFiles);

    switch(fileType) {
      case FileTypes.AUDIO:
        fileName = (new File("./scen/audioFiles.txt")).getPath();
        fileExtension = new String(".mp3");
        minFileSize = this.minAudioSize;
        maxFileSize = this.maxAudioSize;
        break;

      case FileTypes.VIDEO:
        fileName = (new File("./scen/videoFiles.txt")).getPath();
        fileExtension = new String(".mpeg");
        minFileSize = this.minVideoSize;
        maxFileSize = this.maxVideoSize;
        break;

      case FileTypes.DOCUMENT:
        fileName = (new File("./scen/documentFiles.txt")).getPath();
        fileExtension = new String(".pdf");
        minFileSize = this.minDocumentSize;
        maxFileSize = this.maxDocumentSize;
        break;

      case FileTypes.SOFTWARE:
        fileName = (new File("./scen/softwareFiles.txt")).getPath();
        fileExtension = new String(".exe");
        minFileSize = this.minSoftwareSize;
        maxFileSize = this.maxSoftwareSize;
        break;

      default:
        break;
    }
    openFile(fileName);
    writeFile((new Integer(numFilesToGenerate).toString())); // First Write the Number of Files that would be generated ...
    for(int i = 1; i <= numFilesToGenerate; i++) {
      fileSize = (Distribution.getUniformRandom(r, (int)Math.round(minFileSize*1000), (int)Math.round(maxFileSize*1000)))/1000; //kB <---> MB
      int numWords = Distribution.getUniformRandom(r, this.minWords, this.maxWords);
      StringBuffer buffer = new StringBuffer();
      for(int wc = 0; wc < (numWords-1); wc++) {
        buffer.append(new String(dict.getRandomWord()+" "));
      }
      buffer.append(new String(dict.getRandomWord()+fileExtension));
      writeFile(i+": "+numWords+" "+buffer.toString()+" "+(new Double(fileSize)).toString()+" MB");
    }
    closeFile();
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