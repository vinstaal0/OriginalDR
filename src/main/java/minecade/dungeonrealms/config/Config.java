package minecade.dungeonrealms.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;

public class Config {

	
	
	
	
	// Watcha doin here?
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// U sure u wanna be here?
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// Well okay...
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    public static List<String> us_public_servers = new ArrayList<String>(Arrays.asList("US-1", "US-2", "US-3", "US-4"));
    public static List<String> us_beta_servers = new ArrayList<String>(Arrays.asList("US-100"/*, "US-101", "US-102", "US-103", "US-104", "US-105", "US-106", "US-107", "US-108", "US-109", "US-110"*/));
    public static List<String> us_private_servers = new ArrayList<String>(Arrays.asList("US-5"));

    
    public static int transfer_port = 25565;
    //public static String Hive_IP = "72.20.40.38";
//    public static String Hive_IP = "localhost";
    public static String Hive_IP = "192.168.0.106";
//    public static String Hive_IP = "172.17.17.108";

    //public static int SQL_port = 7447;
    public static int SQL_port = 3306;
    //public static String sql_user = "slave_3XNZvi";
//    public static String sql_user = "root";
    public static String sql_user = "dungeonrealms";
//    public static String sql_password = "SgUmxYSJSFmOdro3";
//    public static String sql_password = "qcHqKGVdLXNXXN5j";
//    public static String sql_password = "l8d8X7BRWY4$YXL";
    public static String sql_password = "dungeonrealms";
//    public static String sql_url = "jdbc:mysql://" + Hive_IP + ":" + SQL_port + "/dungeonrealms";
    public static String sql_url = "jdbc:mysql://" + Hive_IP + ":" + SQL_port + "/dungeonrealms?characterEncoding=latin1&useConfigs=maxPerformance";

    public static int FTP_port = 21;
    public static String ftp_user = "agent";
    public static String ftp_pass = "9bgsMKsknkJ6OY"; 
    
    public static String version = "1.9.1";
    
    public static String local_IP = Bukkit.getIp();
    
    public static String realmPath = "/rdata/";
}
