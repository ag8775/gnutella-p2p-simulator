package p2psimulator.util;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

public class Preferences {

    public static String VERSION = "1.0";

    /**
     * Traffic and Topology Scenario Parameters
     */

    public static int minimum_words = 3;

    public static int maximum_words = 8;

    public static int minimum_files = 100;

    public static int maximum_files = 1000;

    public static double minimum_audio_file_size = 1.2; // in MB

    public static double maximum_audio_file_size = 5.5; // in MB

    public static double minimum_video_file_size = 10.5; // in MB

    public static double maximum_video_file_size = 700; // in MB

    public static double minimum_document_file_size = 0.001; // in MB

    public static double maximum_document_file_size = 150; // in MB

    public static double minimum_software_file_size = 1.2; // in MB

    public static double maximum_software_file_size = 700; // in MB

    /**
     * Peer Hardware Parameters
     */

    public static double minimum_bandwidth = 0.056; // in Mbps

    public static double maximum_bandwidth = 1000; // in Mbps

    public static double minimum_computational_power = 266; // in Mhz

    public static double maximum_computational_power = 5000; // in Mhz

    public static int minimum_memory = 32; // in MB

    public static int maximum_memory = 2000; // in MB

    public static int minimum_disk_space = 2000; // in MB

    public static int maximum_disk_space = 200000; // in MB

    /**
     * Network Parameters
     */

    public static long maximum_network_x_dimension = 5000; // im Km

    public static long maximum_network_y_dimension = 5000; // in Km

    public static double minimum_average_geographical_distance_per_hop = 10; // ie 0.01 Km

    public static double maximum_average_geographical_distance_per_hop = 10500; // ie 10.5 Km

    public static double minimum_average_link_delay_per_hop = 0.00005; // in ms/hop/mtu

    public static double maximum_average_link_delay_per_hop = 0.05256; // in ms/hop/mtu

    public static double minimum_router_processing_delay_per_hop = 0.05; // in ms/hop/mtu

    public static double maximum_router_processing_delay_per_hop = 0.25; // in ms/hop/mtu

    public static final int default_MTU = 1500; // in bytes

    /**
     * TIMER PARAMETERS
     */
    public static final double NEIGHBOR_TIMEOUT = 8*60; // in 8 minutes

    public static final double HEARTBEAT_TIMEOUT = 30*60; // in 30 minutes


    /**
        Default maximum queue length for interfaces in bytes.
    */
    public static int default_maxqueuelength = 100000;

    public static final boolean verbose = false;
}
