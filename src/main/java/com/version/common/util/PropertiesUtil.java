package com.version.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PropertiesUtil {
    private static ConcurrentMap<String, String> configMap = new ConcurrentHashMap<String, String>();  
    private static Map<String, Properties> loaderMap = new HashMap<String, Properties>();  
  
    public static String getString(String key, String propName) {  
    	Properties prop = null;  
        try {  
        	prop = getPropFromProperties(propName);  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
        if (!configMap.containsKey(key)) {  
            if (prop.getProperty(key) != null) {  
                configMap.put(key, prop.getProperty(key));  
            }  
        }  
        return configMap.get(key);  
    }  
    public static Properties getPropFromProperties(String fileName) throws Exception {  
        
        Properties prop = loaderMap.get(fileName);  
        if (prop != null) {  
            return prop;  
        }  
        String filePath = null;  
        String configPath = System.getProperty("configurePath");  
  
        if (configPath == null) {  
            filePath = PropertiesUtil.class.getClassLoader().getResource(fileName).getPath();  
        } else {  
            filePath = configPath + "/" + fileName;  
        }  
        prop = new Properties();  
        prop.load(new FileInputStream(new File(filePath)));  
  
        loaderMap.put(fileName, prop);  
        return prop;  
    }  
}
