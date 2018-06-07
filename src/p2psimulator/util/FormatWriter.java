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

public class FormatWriter extends PrintWriter
  {

    private int width = 10; // Feild width required for output

    //Basic Constructor for a default feild width

    public FormatWriter(Writer out)
     {
       super(out); //Call PrintStream Constructor
      }

     //Constructor with a specified feild width

     public FormatWriter(Writer out, int width)
      {
        super(out);   //Call PrintStream Constructor
        this.width = width; // store the Feild width
       }

      //Constructor with autoflush option

      public FormatWriter(Writer out, boolean autoflush)
        {
          super(out, autoflush); //Call PrintStream Constructor
         }
      //Constructor with a specified feild width and autoflush option

      public FormatWriter(Writer out, boolean autoflush, int width)
       {
         super(out, autoflush); //Call PrintStream Constructor
         this.width = width; // store the Feild width
       }


      // Helper Method for Output
      public void output(String str)
       {
         int blanks = width - str.length(); // Number of blanks needed

         // If length is less than the width, add blanks to the start

         if (blanks > 0)
          for (int i = 0; i < blanks; i++)
           super.print(' '); //Output a space

          super.print(str); // Use base method for output
        }




      // Output type long formatted in a given width

      public void print(long value)
       {
         output(Long.toString(value)); // pad to width and output
       }

      // Output type double formatted in a given width

      public void print(double value)
       {
         output(Double.toString(value)); // pad to width and output
       }

       // Output type long formatted in a given width plus a newline

      public void println(long value)
       {
         this.print(value);
         super.println();
       }


        // Output type double formatted in a given width plus a newline

      public void println(double value)
       {
         this.print(value);
         super.println();
       }

       public void setWidth(int width)
        {
          this.width = width ;
        }
    }


