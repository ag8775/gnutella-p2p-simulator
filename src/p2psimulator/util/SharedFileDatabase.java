package p2psimulator.util;
import p2psimulator.element.FileShared;
import p2psimulator.util.FileTypes;
import p2psimulator.util.MD5;
import java.util.*;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class SharedFileDatabase {
  private Hashtable mht; // music Hashtable
  private Hashtable vht; // video Hashtable
  private Hashtable dht; // document Hashtable
  private Hashtable sht; // software Hashtable
  private Hashtable wdht; // word dictionary Hashtable

  private long totalFilesShared;
  private long numMusicFiles;
  private long numVideoFiles;
  private long numDocumentFiles;
  private long numSoftwareFiles;

  public SharedFileDatabase(Vector musicFiles, Vector videoFiles, Vector documentFiles, Vector softwareFiles) {
    mht = new Hashtable();
    vht = new Hashtable();
    dht = new Hashtable();
    sht = new Hashtable();
    wdht = new Hashtable();
    this.numMusicFiles = musicFiles.size();
    this.numVideoFiles = videoFiles.size();
    this.numDocumentFiles = documentFiles.size();
    this.numSoftwareFiles = softwareFiles.size();
    this.totalFilesShared = this.numMusicFiles + this.numVideoFiles + this.numDocumentFiles + this.numSoftwareFiles;
    createHashtables(musicFiles, videoFiles, documentFiles, softwareFiles);
    createWordDictionary(musicFiles, videoFiles, documentFiles, softwareFiles);
  }

  private void createWordDictionary(Vector musicFiles, Vector videoFiles, Vector documentFiles, Vector softwareFiles) {
    createDictionary(wdht, musicFiles);
    createDictionary(wdht, videoFiles);
    createDictionary(wdht, documentFiles);
    createDictionary(wdht, softwareFiles);
  }

  private void createDictionary(Hashtable ht, Vector files) {
     String delimiters = new String(" ");
     Enumeration filesEnum = files.elements();
     while(filesEnum.hasMoreElements()) {
      FileShared fileShared = ((FileShared)(filesEnum.nextElement()));
      String fileName = fileShared.getFileName();
      StringTokenizer st = new StringTokenizer(fileName, delimiters);
      String word;
      Vector sharedFiles = new Vector();
      sharedFiles.addElement(fileShared);
      while(st.hasMoreTokens()) {
        word = st.nextToken();
        //System.out.println("using KEY "+word);
        if(ht.containsKey(word)) {
          sharedFiles.addAll((Vector)(ht.get(word)));
        }
        // some sharedFiles will be same ... a huge redundant database :-(
        Vector uniqueFiles = pruneRedundantFiles(sharedFiles); //Removing Redundant Files  :-)
        sharedFiles.removeAllElements(); // Empty sharedFiles Vector
        sharedFiles.addAll((Vector)uniqueFiles); // put all uniqueFiles
        ht.put(word, sharedFiles); // add Vector uniqueFiles as an Object with key as word
        //ht.put(word, fileShared); // results in non-unique keys ...
        //ht.put(fileName, word); // here fileName is Unique and hence acts as a key ...
      }
     }
  }


  private Vector pruneRedundantFiles(Vector sharedFiles) {
   Vector uniqueFiles = new Vector();
   Hashtable ht = new Hashtable();
   Enumeration fileNames;
   for(int i = 0; i < sharedFiles.size(); i++) {
     FileShared fileShared = (FileShared)(sharedFiles.elementAt(i));
     ht.put(fileShared.getFileName(), fileShared);
   }
   fileNames = ht.keys();
   while(fileNames.hasMoreElements()) {
     String fname = (String) fileNames.nextElement();
     uniqueFiles.addElement(ht.get(fname));
   }
   return uniqueFiles;
  }

  private void createHashtables(Vector musicFiles, Vector videoFiles, Vector documentFiles, Vector softwareFiles) {
    createFilesHashTable(mht, musicFiles);
    createFilesHashTable(vht, videoFiles);
    createFilesHashTable(dht, documentFiles);
    createFilesHashTable(sht, softwareFiles);
  }

  private void createFilesHashTable(Hashtable ht, Vector files) {
    Enumeration filesEnum = files.elements();
    while(filesEnum.hasMoreElements()) {
      FileShared fileShared = ((FileShared)(filesEnum.nextElement()));
      String fileName = fileShared.getFileName();
      ht.put(fileName, fileShared);
    }
  }

  public Vector matchWord(String word) {
    Vector matchedFiles = new Vector();
    if (wdht.containsKey(word)) {
      //System.out.println("contains KEY "+word);
      return (Vector)wdht.get(word);
    }
    return matchedFiles;
  }

 public Vector matchWords(String[] word) {
  Vector matchedFiles = new Vector();
  for(int index = 0; index < word.length; index++) {
    if (wdht.containsKey(word[index])) {
      matchedFiles.addAll((Vector)wdht.get(word[index]));
    }
  }
  return matchedFiles;
 }

 public Vector matchPartialFileName(String[] words, int fileType) {
   Vector matchedFiles = new Vector();
   Vector allFiles = matchWords(words);

   for(int i = 0; i < allFiles.size(); i++) {
     if(((FileShared)(allFiles.elementAt(i))).getFileType() == fileType)
       matchedFiles.addElement(allFiles.elementAt(i));
   }
   return matchedFiles;
 }

 public FileShared matchCompleteFileName(FileShared file) {
  FileShared fileFound = new FileShared();
  String fileName = file.getFileName();
  switch(file.getFileType()) {
    case FileTypes.AUDIO:
      if(mht.containsKey(fileName))
        return (FileShared)(mht.get(fileName));
    case FileTypes.VIDEO:
      if(vht.containsKey(fileName))
        return (FileShared)(vht.get(fileName));
    case FileTypes.DOCUMENT:
      if(dht.containsKey(fileName))
        return (FileShared)(dht.get(fileName));
    case FileTypes.SOFTWARE:
      if(sht.containsKey(fileName))
        return (FileShared)(sht.get(fileName));
  }
  return fileFound;
 }

 public Vector matchFiles(FileShared files[]) {
  Vector matchedFiles = new Vector();
  for(int i = 0; i < files.length; i++) {
    FileShared fileFound = matchCompleteFileName(files[i]);
    if(fileFound.getFileId() != -1)
      matchedFiles.addElement(fileFound);
  }
  return matchedFiles;
 }

}