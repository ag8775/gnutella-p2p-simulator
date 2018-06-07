package p2psimulator.util;
import java.security.*;
import java.lang.Math;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public final class MD5 {

  public static String md5HashCode(String str) throws NoSuchAlgorithmException {
    // JDK supports "SHA" and "MD5" out of the box
    MessageDigest md = MessageDigest.getInstance("MD5");
     // warning: default char encoding is used!
     byte blob[];
     blob = str.getBytes();
     md.reset();
     md.update(blob);
     byte[] md5 = md.digest();
     StringBuffer md5_str = new StringBuffer();

     for (int i = 0; i < md5.length; i++) {
     int j = (int)md5[i];
     if (j < 0) {
       j += 256;
     }
     String s = Integer.toHexString(j);
     if (s.length() == 1) {
       md5_str.append("0");
     }
     md5_str.append(s);
    }
     return md5_str.toString();
  }

  public static String javaHashCode(String str) throws NoSuchAlgorithmException {
      return (new Integer(Math.abs(str.hashCode()))).toString();
  }
}