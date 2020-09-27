package com.apm.pipelines;

import com.apm.inits.BaseCollectionInitialization;
import com.apm.trans.BaseClassFileTransformer;
import com.apm.trans.wrappers.ClassFileTransformerWrappers;
import org.apache.commons.lang3.StringUtils;

/**
 * 默认的拦截器管道实现
 *
 * @author huangfu
 */
public class DefaultInstrumentationPipeline implements InstrumentationPipeline {

    /**
     * 启动引导初始化程序
     */
    private final BaseCollectionInitialization baseCollectionInitialization;

    /**
     * 默认的管道流构造
     *
     * @param baseCollectionInitialization 启动引导初始化程序
     */
    public DefaultInstrumentationPipeline(BaseCollectionInitialization baseCollectionInitialization) {
        this.baseCollectionInitialization = baseCollectionInitialization;
    }


    @Override
    public InstrumentationPipeline addLast(BaseClassFileTransformer baseClassFileTransformer) {
        addLast(null, baseClassFileTransformer);
        return this;
    }

    /**
     * 向链条末尾添加一个
     *
     * @param classFileTransformerName 拦截器名字
     * @param baseClassFileTransformer 拦截器实现
     * @return 本身
     */
    @Override
    public InstrumentationPipeline addLast(String classFileTransformerName, BaseClassFileTransformer baseClassFileTransformer) {
        if (StringUtils.isBlank(classFileTransformerName)) {
            classFileTransformerName = getClassFileTransformerSimpleName(baseClassFileTransformer);
        }
        //包装类格式化器
        ClassFileTransformerWrappers classFileTransformerWrappers = new ClassFileTransformerWrappers(classFileTransformerName, baseClassFileTransformer);
        this.baseCollectionInitialization.addCallChain(classFileTransformerWrappers);
        return this;
    }

    public BaseCollectionInitialization getBaseCollectionInitialization() {
        return baseCollectionInitialization;
    }
}
