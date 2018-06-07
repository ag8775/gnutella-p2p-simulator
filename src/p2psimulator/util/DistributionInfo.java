package p2psimulator.util;
import p2psimulator.prng.Distribution;
import p2psimulator.util.DistributionTypes;
import java.util.*;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class DistributionInfo {
  private int distributionType;
  private Vector distributionParameters; // this is a Vector of strings

  public DistributionInfo() {
    this.distributionType = -1;
  }

  public DistributionInfo(int type, Vector params) {
    this.distributionType = type;
    this.distributionParameters = params;
  }

  public void setDistributionType(int type) {
    this.distributionType = type;
  }

  public void setDistributionParameters(Vector params) {
    this.distributionParameters = params;
  }

  public int getDistributionType() {
    return distributionType;
  }

  public Vector getDistributionParameters() {
    return this.distributionParameters;
  }
}