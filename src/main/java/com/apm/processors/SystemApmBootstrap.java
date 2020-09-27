package com.apm.processors;

import com.apm.inits.BaseCollectionInitialization;
import com.apm.trans.BaseClassFileTransformer;
import com.apm.trans.wrappers.ClassFileTransformerWrappers;

import java.io.*;
import java.lang.instrument.Instrumentation;
import java.util.List;

/**
 * 系统监控启动引导
 *
 * @author huangfu
 */
public class SystemApmBootstrap {
    /**
     * 全局仪表监控管理器
     */
    private final Instrumentation instrumentation;
    /**
     * 参数
     */
    private final String args;

    private BaseCollectionInitialization baseCollectionInitialization;

    /**
     * 系统监控
     *
     * @param instrumentation 监控管理器
     * @param args            参数
     */
    public SystemApmBootstrap(Instrumentation instrumentation, String args) {
        this.instrumentation = instrumentation;
        this.args = args;
    }

    /**
     * 处理器
     *
     * @param baseCollectionInitialization 统计初始化程序
     * @return 当前对象
     */
    public SystemApmBootstrap handler(BaseCollectionInitialization baseCollectionInitialization) {
        baseCollectionInitialization.doInitialization(instrumentation, args);
        this.baseCollectionInitialization = baseCollectionInitialization;
        return this;
    }

    /**
     * 启动
     *
     * @return 启动引导
     */
    public SystemApmBootstrap starter() {
        //获取当前所有的代码格式化器
        List<ClassFileTransformerWrappers> classFileTransformerWrapperList = this.baseCollectionInitialization.getClassFileTransformerWrapperList();
        classFileTransformerWrapperList.forEach(classFileTransformerWrappers -> {
            BaseClassFileTransformer classFileTransformer = classFileTransformerWrappers.getClassFileTransformer();
            //添加类修改器
            this.instrumentation.addTransformer((loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> {
                className = className.replaceAll("/",".");
                //是否匹配预设规则
                if (classFileTransformer.dataMatching(className)) {
                    //调用处理程序
                    byte[] bytes = classFileTransformer.doTransform(loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
                    try {
                        OutputStream out = new FileOutputStream(new File("D:\\javaAgent\\httpService.class"));
                        out.write(bytes);
                        out.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return bytes;
                }
                return null;
            });
        });
        return this;
    }
}
