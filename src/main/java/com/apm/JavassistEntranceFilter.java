package com.apm;

import com.apm.inits.BaseCollectionInitialization;
import com.apm.pipelines.InstrumentationPipeline;
import com.apm.processors.SystemApmBootstrap;
import com.apm.trans.ServiceClassFileTransformer;
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

        SystemApmBootstrap systemApmBootstrap = new SystemApmBootstrap(instrumentation, args);
        //添加一个处理器
        systemApmBootstrap.handler(new BaseCollectionInitialization() {
            @Override
            public void initialization(InstrumentationPipeline instrumentationPipeline) {
                instrumentationPipeline.addLast(new ServiceClassFileTransformer());
            }
        }).starter();

    }
}
