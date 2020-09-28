package com.apm.trans;

import com.apm.adapters.HttpServletRequestAdapter;
import com.apm.constants.StatisticsClassTemplateConstants;
import com.apm.entity.HttpStatisticsEntity;
import javassist.*;

/**
 * http方法转换器
 *
 * @author huangfu
 */
public class HttpClassFileTransformer extends BaseClassFileTransformer {
    /**
     * servlet类
     */
    private static final String TARGET_CLASS = "javax.servlet.http.HttpServlet";
    /**
     * http请求必回经由service方法
     */
    private static final String TARGET_METHOD = "service";


    @Override
    public CtClass buildCtClass(ClassLoader loader, String className) throws Exception {
        //类池
        ClassPool classPool = new ClassPool();
        //加载当前项目内的所由类路径
        classPool.insertClassPath(new LoaderClassPath(loader));
        //获取类的操作对象
        CtClass ctClass = classPool.get(TARGET_CLASS);
        //获取方法对象
        CtMethod declaredMethod = ctClass.getDeclaredMethod(TARGET_METHOD);
        //构建方法
        buildMethod(ctClass, declaredMethod);
        return ctClass;
    }

    @Override
    public void buildMethod(CtClass ctClass, CtMethod ctMethod) throws Exception {
        //赋值一个方法
        CtMethod copyCtMethod = CtNewMethod.copy(ctMethod, ctClass, null);
        //修改原始方法方法名称
        ctMethod.setName(javaAgentMethodNameBuild(ctMethod.getName()));
        //构建一个统计对象
        String entitySrc = "com.apm.entity.HttpStatisticsEntity httpStatisticsEntity = new com.apm.entity.HttpStatisticsEntity()";
        String beginSrc = "com.apm.utils.StatisticsMethodUtils.begin(httpStatisticsEntity,\"http\")";
        String errorSrc = "com.apm.utils.StatisticsMethodUtils.setErrorData(httpStatisticsEntity,e)";
        String endSrc = "com.apm.utils.StatisticsMethodUtils.end(httpStatisticsEntity)";
        String methodData = "com.apm.trans.HttpClassFileTransformer.httpStatisticsEntityBuild(httpStatisticsEntity,$args)";
        String pushData = "System.out.println(httpStatisticsEntity)";

        String methodBody = String.format(StatisticsClassTemplateConstants.VOID_METHOD_TYPE_TEMPLATE, entitySrc, beginSrc, ctMethod.getName(), errorSrc, methodData, endSrc, pushData);
        copyCtMethod.setBody(methodBody);
        ctClass.addMethod(copyCtMethod);
    }

    /**
     * 构建一个 httpStatisticsEntity
     *
     * @param httpStatisticsEntity 数据载体
     * @param args                 参数
     */
    public static void httpStatisticsEntityBuild(HttpStatisticsEntity httpStatisticsEntity, Object[] args) {
        Object httpRequest = args[0];
        HttpServletRequestAdapter httpServletRequestAdapter = new HttpServletRequestAdapter(httpRequest);
        httpStatisticsEntity.setMethod(httpServletRequestAdapter.getMethod());
        httpStatisticsEntity.setHandlerMap(httpServletRequestAdapter.getHeaderMap());
        httpStatisticsEntity.setContextPath(httpServletRequestAdapter.getContextPath());
        httpStatisticsEntity.setUri(httpServletRequestAdapter.getRequestUri());
        httpStatisticsEntity.setUrl(httpServletRequestAdapter.getRequestUrl());
    }


    @Override
    public boolean dataMatching(String verifyTheData) {
        return TARGET_CLASS.equals(verifyTheData);
    }
}
