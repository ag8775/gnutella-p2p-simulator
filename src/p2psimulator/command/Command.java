package p2psimulator.command;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

/**
   Command is a very important class in P2P Simulator because it represents the commands
   that the simulator is supposed to execute at a specific time.<br>
   You have to subclass command to make use of it. You can then use it to
   schedule a call to your class' foobar() function after x seconds.
*/
public abstract class Command {

    String m_name;         // Name of the command (be artistic)
    double m_time;         // The time at which the command should be executed

    /**
       Create a new command class with a specific name to execute at some
       specific time.
       @param name name of the command
       @param time the time at which to execute the command
    */
    public Command(String name,double time) {
      m_name=name;
      m_time=time;
    }


    /**
       Return the command's execution time.
    */
    public double getTime() {
      return m_time;
    }

    /**
       Return the command's name.
    */
    public String getName() {
      return m_name;
    }

    /**
       Debugging output, print's the command's name.
    */
    public void dump() {
      System.out.println(m_name+": "+m_time);
    }

    /**
       The execution function. Has to be overridden by subclasses.
     */
    public abstract void execute();

}
