package p2psimulator.element;
import p2psimulator.util.FileTypes;
/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class FileShared {
  private int fileSize;
  private int fileType;
  private String fileName; // dont include fileExtension in fileName, its IMPLICIT :-)
  private long file_id; // md5/java hash code ...

  public FileShared() {
    this.fileName = new String();
    this.file_id = -1;
    this.fileType = -1;
    this.fileSize = -1;
  }

  public FileShared(String name, int fileType) {
    this.fileName = name;
    this.file_id = 1; // dummy value
    this.fileType = fileType;
    this.fileSize = 1; // dummy value
  }

  public FileShared(String name, long fileId, int fileType, int fileSize) {
    this.fileName = name;
    this.file_id = fileId;
    this.fileType = fileType;
    this.fileSize = fileSize;
  }

  public String getFileName() {
    return this.fileName;
  }

  public long getFileId() {
    return this.file_id;
  }

  public int getFileType() {
    return this.fileType;
  }

  public int getFileSize() {
    return this.fileSize;
  }

  public void setFileName(String fileName) {
     this.fileName = fileName;
   }

   public void setFileId(long fileId) {
     this.file_id = fileId;
   }

   public void setFileType(int fileType) {
     this.fileType = fileType;
   }

   public void setFileSize(int fileSize) {
     this.fileSize = fileSize;
  }
}