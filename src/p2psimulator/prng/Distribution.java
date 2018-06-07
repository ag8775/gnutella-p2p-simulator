package p2psimulator.prng;
import java.util.Random;
import p2psimulator.util.DistributionInfo;
import p2psimulator.util.DistributionTypes;
import java.lang.Math;
import java.util.Vector;
/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */


public final class Distribution {


    public static double getUniformRandom(Random r) {
        return r.nextDouble();
    }

    /*returns a rand between low (exclusive) and high (inclusive)*/
    public static int getUniformRandom(Random r, int low, int high) {
        int n=0;
        while (n==0)
            n = r.nextInt(high); /*this gives me a number between 0 and high, inclusive*/
        if((n+low) > high)
          return n;
        return n+low; /*shift*/
    }

     /*returns a rand between low (exclusive) and high (inclusive)*/
    public static double getUniformRandom(Random r, double low, double high) {
        return low+(r.nextDouble()*high);
    }

    //-------------------------------- -----------------------
    public static double getGaussianRandom(Random r, double mean, double std) {
        return ( r.nextGaussian()*std+mean);
    }


    public static double getParetoRandom (Random r, double K, double P, double ALPHA) {

        double x = r.nextDouble();
        double    den =Math.pow(1.0-x+x*Math.pow(K/P, ALPHA), 1/ALPHA);
        while (den==0) {
            x = r.nextDouble();
            den =Math.pow(1.0-x+x*Math.pow(K/P, ALPHA), 1/ALPHA);
        }
        return (K/den);

    }

    public static double getParetoRandom (Random r, double scale, double shape) {

        double x = r.nextDouble();
        double    den =Math.pow(1.0-x+x*Math.pow(1.0/scale, shape), 1/shape);
        while (den==0) {
            x = r.nextDouble();
            den =Math.pow(1.0-x+x*Math.pow(1.0/scale, shape), 1/shape);
        }
        return (1/den);

    }

    public static double  getExponentialRandom(Random r, double lambda) {
        double u = getUniformRandom(r);
        return -(1.0/lambda)*Math.log(u);
    };

    public static double getZipfRandom(Random r) {
      double rn = -1;


      return rn;
    }

    public static double getRandomNumber(DistributionInfo distInfo, Random r) {
      double rn = -1;
      int distributionType = distInfo.getDistributionType();
      Vector distributionParams = distInfo.getDistributionParameters();

      switch(distributionType) {
        case DistributionTypes.JAVA_RANDOM:
          rn = r.nextDouble();
          break;

        case DistributionTypes.UNIFORM_RANDOM:
          rn = r.nextDouble();
          break;

        case DistributionTypes.UNIFORM_RANDOM_WITHIN_INTERVAL:
          int low = ((Integer)(distributionParams.elementAt(0))).intValue();
          int high = ((Integer)(distributionParams.elementAt(1))).intValue();
          rn = getUniformRandom(r, low, high);
          break;

        case DistributionTypes.EXPONENTIAL_RANDOM:
          double lambda = ((Double)(distributionParams.elementAt(0))).doubleValue();
           rn = getExponentialRandom(r, lambda);
          break;

        case DistributionTypes.GAUSSIAN_RANDOM:
          double mean = ((Double)(distributionParams.elementAt(0))).doubleValue();
          double std = ((Double)(distributionParams.elementAt(1))).doubleValue();
          rn = getGaussianRandom(r, mean, std);
          break;

        case DistributionTypes.PARETO_RANDOM:
          double K = ((Double)(distributionParams.elementAt(0))).doubleValue();
          double P = ((Double)(distributionParams.elementAt(1))).doubleValue();
          double ALPHA = ((Double)(distributionParams.elementAt(2))).doubleValue();
          rn = getParetoRandom (r, K, P, ALPHA);
          break;

        case DistributionTypes.PARETO_RANDOM_WITH_SHAPE:
          double scale = ((Double)(distributionParams.elementAt(0))).doubleValue();
          double shape = ((Double)(distributionParams.elementAt(1))).doubleValue();
          rn = getParetoRandom (r, scale, shape);
          break;

        case DistributionTypes.ZIPF_RANDOM:
          break;
      }
    return rn;
  }
}
