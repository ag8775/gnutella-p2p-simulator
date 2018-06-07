package p2psimulator;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

import java.util.Vector;
import java.util.Enumeration;
import p2psimulator.element.Element;
import p2psimulator.util.PriorityQueue;
import p2psimulator.util.Preferences;
import p2psimulator.command.*;

/**
   Simulator is the main class in P2P. It contains the main loop that will
   update the elements of the network. Objects can request to have their own
   commands scheduled here to have them executed at a specific time.
*/
public class Simulator {

    private Vector m_elements;

    private double m_time;              // Time in seconds

    private static Simulator m_instance=null;

    private boolean m_finished;         // Flag to finish the run() loop

    private PriorityQueue m_commands;   // Queue of Command objects


    /**
       Default Constructor. Initialises the internal structure and resets the
       time to zero. You cannot call this from outside, this is a singleton
       class. Use the getInstance function.
       @see getInstance
    */
    private Simulator() {
      m_elements=new Vector();
      m_commands=new PriorityQueue();
      m_time=0;
      m_finished=false;
    }


    /**
       Return the singleton instance of the simulator. There can only ever be
       one simulator active at a time.
       @return the one and only simulator object
    */
    public static Simulator getInstance() {
      if (m_instance==null)
      m_instance=new Simulator();
      return m_instance;
    }


    /**
       Attach a new element to the simulator to be updated regularly during
       the simulation.
       @param element the element to add
    */
    public void attach(Element element) {
      Enumeration e=m_elements.elements();

      m_elements.addElement(element);
    }

    /**
       Add a command the simulator should execute at a specific time.
       @param command the command object that should be executed
    */
    public void schedule(Command command) {
      m_commands.push(command,command.getTime());
    }


    /**
       Dump the contents of the simulator, for debugging purposes. This
       function calls the dump function of all elements in the simulator.
    */
    public void dump() {


      System.out.println("----------[STATIC ELEMENTS]----------");

      Enumeration e=m_elements.elements();
      while (e.hasMoreElements()) {
        Element curelement=(Element)e.nextElement();

        curelement.dump();
        System.out.println("---------------");
      }

      System.out.println("------------[COMMANDS]---------------");
      e=m_commands.elements();
      while (e.hasMoreElements()) {
        Command curcommand=(Command)e.nextElement();
        System.out.println(curcommand.getName()+": "+curcommand.getTime());
      }
    }


    /**
       Enumerate the elements stored in the simulator. This can be used by
       other objects to, say, print them out.
       @return an Enumeration of the elements in the simulator
    */
    public Enumeration enumerateElements() {
      return m_elements.elements();
    }


    /**
       Run the actual simulation. This takes over control from the calling
       program for a long period of time, don't expect to do anything
       in the meantime.
       After the execution of run(), the contents of the simulator and all its
       elements are invalid.
    */
    public void run() {


      while (m_commands.size()>0 && !m_finished) {
        Command current_command=(Command)m_commands.peek();
        m_commands.pop();

        // Display extra information if it is needed
        if (Preferences.verbose)
        current_command.dump();

        m_time=current_command.getTime();
        current_command.execute();
      }

    }


    /**
       error can be used by elements to display an error message and exit
       immediately, if the error cannot be recovered.
       @param message the error message to display. It is prepended with the
       string "*P2P ERROR*".
    */
    public static void error(String message) {
      System.err.println("*P2P ERROR* "+message);
      System.exit(1);
    }


    /**
       warning can be used by elements to display information about non-fatal
       errors.
       @param message the warning message to display. It is prepended with the
       string "*P2P WARNING*".
    */
    public static void warning(String message) {
      System.err.println("*P2P WARNING* "+message);
    }


    /**
       verbose is used to print out messages if the simulator is in verbose
       mode. Elements use this for debugging output. Nothing will be displayed
       if the Preferences.verbose variable is set to false.
       @param message the string to display
    */
    public static void verbose(String message) {
      if (Preferences.verbose)
      System.out.println(message);
    }


    /**
       This function is just an aid that allows printing of integers. It will
       convert the integer to a string and forward the call to the
       verbose(String) function.
       @param i the integer to print out
    */
    public static void verbose(int i) {
      Simulator.verbose(String.valueOf(i));
    }


    /**
       Return the current time in the simulation, in seconds.
       @return the current time in seconds
    */
    public double getTime() {
      return m_time;
    }


    /**
       Set a flag that will quit the simulator immediately.
    */
    public void setFinished() {
      m_finished=true;
    }


}

