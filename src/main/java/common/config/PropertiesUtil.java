package common.config;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

public class PropertiesUtil {
	
	public static Map<String, String> config;
	private static Logger logger = Logger.getLogger(PropertiesUtil.class);
	private final static String path = "deltaTest.properties";
	
	static{
		loadProperties();
	}
	
	public static String get(String key){
		return config.get(key);
	}
	
	public static void loadProperties() {
		logger.info("laod configuration file [owl.properties]... ");
		Properties props = new Properties();
		config = new HashMap<String, String>();

		try {
			FileInputStream in = new FileInputStream(path);
			props.load(in);
			in.close();
			Set<Object> keys = props.keySet();
			for (Iterator<Object> it = keys.iterator(); it.hasNext();) {
				String key = (String) it.next();
				String value = props.getProperty(key);
				config.put(key, value);
				logger.info(key + ":" + value);
			}
		} catch (Exception e) {
			logger.error("Error in load Properties.");
			e.printStackTrace();
		}
		logger.info("Done.");
	}
}
