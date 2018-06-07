package p2psimulator.test;
import p2psimulator.util.SharedFileDatabase;
import p2psimulator.element.FileShared;
import p2psimulator.util.FileTypes;
import java.util.*;
/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class TestSharedFileDatabase {

  public static void main(String[] args) {
    Vector musicFiles, videoFiles, documentFiles, softwareFiles;
    musicFiles = new Vector();
    videoFiles = new Vector();
    documentFiles = new Vector();
    softwareFiles = new Vector();
    FileShared fileShared;

    fileShared = new FileShared("All Things Are Possible", 1, FileTypes.AUDIO, 4) ;
    musicFiles.addElement(fileShared);
    fileShared = new FileShared("Open the Eyes of My Heart", 1, FileTypes.AUDIO, 4) ;
    musicFiles.addElement(fileShared);
    fileShared = new FileShared("Message From the Heart", 1, FileTypes.AUDIO, 4) ;
    musicFiles.addElement(fileShared);
    fileShared = new FileShared("Great is Thy Faithfulness", 1, FileTypes.AUDIO, 4) ;
    musicFiles.addElement(fileShared);

    fileShared = new FileShared("The League of Extraordinary Gentlemen", 1, FileTypes.VIDEO, 4) ;
    videoFiles.addElement(fileShared);
    fileShared = new FileShared("Pirates of The Caribbean Site Exclusive", 1, FileTypes.VIDEO, 4) ;
    videoFiles.addElement(fileShared);
    fileShared = new FileShared("The Dancer Upstairs", 1, FileTypes.VIDEO, 4) ;
    videoFiles.addElement(fileShared);
    fileShared = new FileShared("Better Luck Tomorrow", 1, FileTypes.VIDEO, 4) ;
    videoFiles.addElement(fileShared);

    fileShared = new FileShared("What a Girl Wants", 1, FileTypes.DOCUMENT, 4) ;
    documentFiles.addElement(fileShared);
    fileShared = new FileShared("Johnny English", 1, FileTypes.DOCUMENT, 4) ;
    documentFiles.addElement(fileShared);
    fileShared = new FileShared("The Man Without A Past", 1, FileTypes.DOCUMENT, 4) ;
    documentFiles.addElement(fileShared);
    fileShared = new FileShared("DysFunKtional Family", 1, FileTypes.DOCUMENT, 4) ;
    documentFiles.addElement(fileShared);

    fileShared = new FileShared("PocoMail My email client of choice", 1, FileTypes.SOFTWARE, 4) ;
    softwareFiles.addElement(fileShared);
    fileShared = new FileShared("Mozilla The only browser you need", 1, FileTypes.SOFTWARE, 4) ;
    softwareFiles.addElement(fileShared);
    fileShared = new FileShared("AceHTML A shareware clone of Homesite", 1, FileTypes.SOFTWARE, 4) ;
    softwareFiles.addElement(fileShared);
    fileShared = new FileShared("Cetus CNote Text Editor A Windows Notepad replacement with multi document interface and spell check", 1, FileTypes.SOFTWARE, 4) ;
    softwareFiles.addElement(fileShared);

    SharedFileDatabase sharedFileDatabase = new SharedFileDatabase(musicFiles, videoFiles, documentFiles, softwareFiles) ;

    Vector filesMatched = sharedFileDatabase.matchWord(new String("DysFunKtional"));
    for(int i = 0; i < filesMatched.size(); i++)
      System.out.println("File Matching for Word DysFunKtional is "+((FileShared)(filesMatched.elementAt(i))).getFileName());

  }
}