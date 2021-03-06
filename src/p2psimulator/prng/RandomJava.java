package p2psimulator.prng;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */


/**
 * <p>Title: Sensor Topology Viewer Source Code</p>
 * <p>Description: This Program implements a Random Topology Generator and Viewer</p>
 * <p>Copyright: Copyright (mk) 2002 </p>
 * <p>Company: Networking Wireless Sensors Lab (NeWS), Wayne State University</p>
 * @author Manish M Kochhal
 * @version 1.0
 */


import java.util.*;

//
// RngPack 1.0 by Paul Houle
// http://www.msc.cornell.edu/~houle/rngpack
//

/**
* RandomJava is a class wrapper for the <CODE>Math.random()</CODE>
* generator that comes with Java.  I know nothing about the
* quality of <CODE>Math.random()</CODE>, but I will warn the
* reader that system-supplied
* RNGs have a bad reputation;  <TT>RandomJava</TT> is <B>NOT</B>
* reccomended for general use,  it has only been included as a
* straightforward example of how to
* build a <CODE>RandomElement</CODE> wrapper for an existing RNG.
* The <TT>RANMAR</TT>, <TT>RANECU</TT> and <TT>RANLUX</TT>
* generators included in this package all appear to be faster than
* Math.random();  all three are well-studied,  portable and
* proven in use.
*
* <P>
* <A HREF="../src/edu/cornell/lassp/houle/RngPack/RandomJava.java" TARGET="edu.cornell.lassp.houle.source">
* Source code </A> is available.
*
* @author <A HREF="http://www.msc.cornell.edu/~houle" TARGET="edu.cornell.lassp.houle.author"> Paul Houle </A> (E-mail: <A HREF="mailto:houle@msc.cornell.edu">houle@msc.cornell.edu</A>)
* @version 1.0
* @see Ranmar
* @see Ranlux
* @see Ranecu
*/

public class RandomJava extends RandomElement {

/**
* Wrapper for <CODE>Math.random().</CODE>
@see RandomElement#raw
*/
  public double raw() {
          return Math.random();
  };

};

