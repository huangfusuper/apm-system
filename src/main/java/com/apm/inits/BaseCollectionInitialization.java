package com.apm.inits;

import com.apm.pipelines.DefaultInstrumentationPipeline;
import com.apm.pipelines.InstrumentationPipeline;
import com.apm.trans.wrappers.ClassFileTransformerWrappers;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务监控快速启动引导类
 *
 * @author huangfu
 * @date 2020年9月27日09:49:05
 */
public abstract class BaseCollectionInitialization {
    private final List<ClassFileTransformerWrappers> classFileTransformerWrapperList = new ArrayList<>(8);

    /**
     * 全局仪表监控管理器
     */
    private Instrumentation instrumentation;
    /**
     * 参数
     */
    private String args;

    /**
     * 拦截器管道
     */
    private final InstrumentationPipeline instrumentationPipeline;

    public BaseCollectionInitialization() {
        this.instrumentationPipeline = newInstrumentationPipeline();
    }

    /**
     * 统计初始化
     *
     * @param instrumentationPipeline 类格式化管理通道
     */
    public abstract void initialization(InstrumentationPipeline instrumentationPipeline);

    /**
     * 调用实际的抽象方法
     */
    public void doInitialization(Instrumentation instrumentation, String args) {
        this.instrumentation = instrumentation;
        this.args = args;
        initialization(instrumentationPipeline);
    }

    /**
     * 使用默认的管理器
     *
     * @return 默认的管道管理器
     */
    public InstrumentationPipeline newInstrumentationPipeline() {
        return new DefaultInstrumentationPipeline(this);
    }

    /**
     * 向调用链添加一个处理器
     * @param classFileTransformerWrappers 处理器包装类
     */
    public void addCallChain(ClassFileTransformerWrappers classFileTransformerWrappers){
        this.classFileTransformerWrapperList.add(classFileTransformerWrappers);
    }

    public List<ClassFileTransformerWrappers> getClassFileTransformerWrapperList() {
        return classFileTransformerWrapperList;
    }

    public InstrumentationPipeline getInstrumentationPipeline() {
        return instrumentationPipeline;
    }

    public Instrumentation getInstrumentation() {
        return instrumentation;
    }

    public void setInstrumentation(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }


}
