package p2psimulator.generators;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class Coordinates {

  private int nodeId;
  private long coordX;
  private long  coordY;

  public Coordinates(int nodeId, long coordX, long coordY) {
  this.nodeId = nodeId;
  this.coordX = coordX;
  this.coordY = coordY;
  }

  public Coordinates(int nodeId) {
  this.nodeId = nodeId;
  }

  public void setId(int nodeId)
   {
     this.nodeId = nodeId;
   }

  public void setCoords(long coordX, long coordY)
   {
     this.coordX = coordX;
     this.coordY = coordY;
   }

   public void setX(long coordX)
   {
     this.coordX = coordX;

   }

   public void setY(long coordY)
   {
     this.coordY = coordY;
   }

  public int getId()
   {
     return this.nodeId;
   }

  public long getX()
   {
     return this.coordX;
   }

  public long getY()
   {
     return this.coordY;
   }
}