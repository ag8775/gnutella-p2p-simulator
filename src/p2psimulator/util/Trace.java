package p2psimulator.util;
import java.io.*;
/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class Trace {
        private String traceFileName;
        private FormatWriter fileout ;


        public Trace(String fileName) {
                openTraceFile(fileName);
        }

        public void openTraceFile(String fileName)
    {
      try {
            this.traceFileName = new String(fileName);
            this.fileout = new FormatWriter(new BufferedWriter(new FileWriter(traceFileName)), 5);
          }
      catch (IOException ae)
       {
          System.out.println("IO exception thrown: " + ae);
       }
    }

  public void closeTraceFile()
   {
     try {
           fileout.close();
         }
     catch (Exception io)
      {
       System.out.println("Error during closing Trace file");
      }
  }

  public void dumpTrace(String str)
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

} // End of Trace Class ...