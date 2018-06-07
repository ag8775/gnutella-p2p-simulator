package p2psimulator.util;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class FileTypes {
    public final static int DOCUMENT = 1;
    public final static int SOFTWARE = 2;
    public final static int AUDIO = 3;
    public final static int VIDEO = 4;

    public final static String AUDIO_EXTENSION = ".mp3";
    public final static String VIDEO_EXTENSION = ".mpeg";
    public final static String DOCUMENT_EXTENSION = ".pdf";
    public final static String SOFTWARE_EXTENSION = ".exe";

    public static int getFileType(String extension) {
      if(extension.compareTo(AUDIO_EXTENSION) == 0)
        return AUDIO;

      if(extension.compareTo(VIDEO_EXTENSION) == 0)
        return VIDEO;

      if(extension.compareTo(DOCUMENT_EXTENSION) == 0)
        return DOCUMENT;

      if(extension.compareTo(SOFTWARE_EXTENSION) == 0)
        return SOFTWARE;

      return -1;
    }
}