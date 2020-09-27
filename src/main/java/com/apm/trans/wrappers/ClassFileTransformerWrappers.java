package com.apm.trans.wrappers;

import com.apm.trans.BaseClassFileTransformer;

/**
 * 转换器包装器
 *
 * @author huangfu
 */
public class ClassFileTransformerWrappers {
    /**
     * 类格式化器名称
     */
    private String classFileTransformerName;
    /**
     * 类转换器
     */
    private BaseClassFileTransformer classFileTransformer;

    public ClassFileTransformerWrappers(String classFileTransformerName, BaseClassFileTransformer classFileTransformer) {
        this.classFileTransformerName = classFileTransformerName;
        this.classFileTransformer = classFileTransformer;
    }

    public BaseClassFileTransformer getClassFileTransformer() {
        return classFileTransformer;
    }

    public void setClassFileTransformer(BaseClassFileTransformer classFileTransformer) {
        this.classFileTransformer = classFileTransformer;
    }

    public String getClassFileTransformerName() {
        return classFileTransformerName;
    }

    public void setClassFileTransformerName(String classFileTransformerName) {
        this.classFileTransformerName = classFileTransformerName;
    }
}
