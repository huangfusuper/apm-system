package com.apm.utils;

import com.apm.exceptions.ApmException;
import org.apache.commons.lang3.StringUtils;

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

    /**
     * 返回配置文件里面k-v
     * @param javaAgentPropertiesKey 主键
     * @return 对应的值 为空则返回默认值
     */
    public static String getConfig(JavaAgentPropertiesKey javaAgentPropertiesKey) {
        String keyName = javaAgentPropertiesKey.getKeyName();
        String value = JAVA_AGENT_CONFIG_MAP.get(keyName);
        if(StringUtils.isNoneBlank(value)){
            return value;
        }else {
            return javaAgentPropertiesKey.getDefaultValue();
        }
    }
}
