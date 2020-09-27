package com.apm.pipelines;

import com.apm.trans.BaseClassFileTransformer;

/**
 * 拦截器的管道流
 *
 * @author huangfu
 */
public interface InstrumentationPipeline {

    /**
     * 向调用链后面添加一个拦截器
     *
     * @param baseClassFileTransformer 拦截器实现
     * @return 当前对象以便于链式调用
     */
    InstrumentationPipeline addLast(BaseClassFileTransformer baseClassFileTransformer);

    /**
     * 向调用链后面添加一个拦截器
     *
     * @param baseClassFileTransformer 拦截器实现
     * @param classFileTransformerName 拦截器名字
     * @return 当前对象以便于链式调用
     */
    InstrumentationPipeline addLast(String classFileTransformerName, BaseClassFileTransformer baseClassFileTransformer);

    /**
     * 获取拦截器的简单名称
     *
     * @param baseClassFileTransformer 拦截器
     * @return 拦截器名称
     */
    default String getClassFileTransformerSimpleName(BaseClassFileTransformer baseClassFileTransformer) {
        String classFileTransformerNameTemplate = "systemApm#%s";
        String simpleName = baseClassFileTransformer.getClass().getSimpleName();
        return String.format(classFileTransformerNameTemplate, simpleName);
    }
}
