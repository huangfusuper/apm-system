package com.apm;

import com.apm.inits.BaseCollectionInitialization;
import com.apm.pipelines.InstrumentationPipeline;
import com.apm.processors.SystemApmBootstrap;
import com.apm.trans.HttpClassFileTransformer;
import com.apm.trans.ServiceClassFileTransformer;
import com.apm.utils.JavaAgentPropertiesKey;
import com.apm.utils.JavaAgentPropertiesUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.instrument.Instrumentation;
import java.util.Objects;

/**
 * JavassistEntrance 字节码入口拦截器
 *
 * @author huangfu
 * @date 2020年9月25日08:57:22
 */
public class JavassistEntranceFilter {

    /**
     * java agent 的配置文件
     */
    public static final String JAVA_AGENT_PROPERTIES = "java-agent.properties";

    public static void premain(String args, Instrumentation instrumentation) {
        if (StringUtils.isBlank(args)) {
            args = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(JAVA_AGENT_PROPERTIES)).getPath();
        }
        //解析指定配置文件
        JavaAgentPropertiesUtil.parseProperties(args);
        //创建启动引导
        SystemApmBootstrap systemApmBootstrap = new SystemApmBootstrap(instrumentation, args);
        //添加一个处理器
        systemApmBootstrap.handler(new BaseCollectionInitialization() {
            @Override
            public void initialization(InstrumentationPipeline instrumentationPipeline) {
                //是否开启service方法监控
                String enableServiceMonitor = JavaAgentPropertiesUtil.getConfig(JavaAgentPropertiesKey.ENABLE_SERVICE_MONITOR);
                //是否开启http方法监控
                String enableHttpMonitor = JavaAgentPropertiesUtil.getConfig(JavaAgentPropertiesKey.ENABLE_HTTP_MONITOR);

                //添加service方法监控
                if (Boolean.parseBoolean(enableServiceMonitor)) {
                    instrumentationPipeline.addLast(new ServiceClassFileTransformer());
                }
                //添加http方法监控
                if(Boolean.parseBoolean(enableHttpMonitor)) {
                    instrumentationPipeline.addLast(new HttpClassFileTransformer());
                }

            }
        }).starter();

    }
}
