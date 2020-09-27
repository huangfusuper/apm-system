package com.apm.utils;

import com.apm.exceptions.ApmException;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JavaAgent 配置读取工具类
 *
 * @author huangfu
 */
public class JavaAgentPropertiesUtil {
    /**
     * 存储配置文件信息
     */
    private static final Map<String,String> JAVA_AGENT_CONFIG_MAP = new ConcurrentHashMap<>(8);

    /**
     * 解析java agent 配置文件
     * @param configPath 配置文件路径
     */
    public static void parseProperties(String configPath){
        synchronized (JAVA_AGENT_CONFIG_MAP) {
            Properties props = new Properties();
            try(InputStream in = new FileInputStream(configPath)) {
                props.load(in);
            }catch (Exception e) {
                throw ApmException.wrap(e);
            }

            props.forEach((key,value) ->{
                JavaAgentPropertiesUtil.JAVA_AGENT_CONFIG_MAP.put((String)key,(String)value);
            });
        }
    }

    public static String getConfig(JavaAgentPropertiesKey javaAgentPropertiesKey) {
        String keyName = javaAgentPropertiesKey.getKeyName();
        return JAVA_AGENT_CONFIG_MAP.get(keyName);
    }
}
