package com.apm.trans;

import com.apm.constants.StatisticsClassTemplateConstants;
import com.apm.entity.ServiceStatisticsEntity;
import com.apm.exceptions.ApmException;
import javassist.*;

import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * 业务类转换器
 *
 * @author huangfu
 */
public class ServiceClassFileTransformer extends BaseClassFileTransformer {

    public static final String VOID_TYPE_NAME = "void";

    @Override
    public byte[] doTransform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            return buildCtClass(loader, className).toBytecode();
        } catch (Exception e) {
            throw ApmException.wrap(e);
        }
    }

    public CtClass buildCtClass(ClassLoader loader, String className) throws NotFoundException, CannotCompileException {
        //定义类池
        ClassPool pool = new ClassPool();
        //加载当前项目内的所由类路径
        pool.insertClassPath(new LoaderClassPath(loader));
        //获取类的操作对象
        CtClass ctClass = pool.get(className);
        //获取方法级操作对象
        CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
        for (CtMethod declaredMethod : declaredMethods) {
            //屏蔽非公共方法
            if (!Modifier.isPublic(declaredMethod.getModifiers())) {
                continue;
            }
            //屏蔽静态方法
            if (Modifier.isStatic(declaredMethod.getModifiers())) {
                continue;
            }
            //屏蔽本地方法
            if (Modifier.isNative(declaredMethod.getModifiers())) {
                continue;
            }
            //修改原始方法
            buildMethod(ctClass, declaredMethod);
        }
        return ctClass;
    }

    /**
     * 构建统计方法对象
     *
     * @param ctClass  类对象
     * @param ctMethod 方法对象
     */
    public void buildMethod(CtClass ctClass, CtMethod ctMethod) throws CannotCompileException, NotFoundException {
        CtClass returnType = ctMethod.getReturnType();
        //拷贝一个方法 此方法为原始方法，修改他的类名，然后由新增方法调用该方法
        CtMethod copyCtMethod = CtNewMethod.copy(ctMethod, ctClass, null);
        //修改新名称
        ctMethod.setName(javaAgentMethodNameBuild(copyCtMethod.getName()));
        //构建一个统计对象
        String entitySrc = "com.apm.entity.ServiceStatisticsEntity serviceStatisticsEntity = new com.apm.entity.ServiceStatisticsEntity()";
        String beginSrc = "com.apm.utils.StatisticsMethodUtils.begin(serviceStatisticsEntity,\"service\")";
        String errorSrc = "com.apm.utils.StatisticsMethodUtils.setErrorData(serviceStatisticsEntity,e)";
        String endSrc = "com.apm.utils.StatisticsMethodUtils.end(serviceStatisticsEntity)";
        String pushData = "System.out.println(serviceStatisticsEntity)";
        if (VOID_TYPE_NAME.equals(returnType.getName())) {
            String voidServiceMethodTemplate = "com.apm.trans.ServiceClassFileTransformer.setServiceMethodData(serviceStatisticsEntity, \"%s\", \"%s\", \"%s\", null, $args,\"void\")";
            String voidServiceMethod = String.format(voidServiceMethodTemplate, ctClass.getName(), ctClass.getSimpleName(), copyCtMethod.getName());
            String voidSrcCode = String.format(StatisticsClassTemplateConstants.VOID_METHOD_TYPE_TEMPLATE, entitySrc, beginSrc, ctMethod.getName(), errorSrc, voidServiceMethod, endSrc, pushData);
            copyCtMethod.setBody(voidSrcCode);
        } else {
            String returnServiceMethodTemplate = "com.apm.trans.ServiceClassFileTransformer.setServiceMethodData(serviceStatisticsEntity, \"%s\", \"%s\", \"%s\", result$ApmAgent, $args,\"%s\")";
            String returnServiceMethod = String.format(returnServiceMethodTemplate, ctClass.getName(), ctClass.getSimpleName(), copyCtMethod.getName(), returnType.getName());
            String returnSrcCode = String.format(StatisticsClassTemplateConstants.RETURN_METHOD_TYPE_TEMPLATE, entitySrc, beginSrc, ctMethod.getName(), errorSrc, returnServiceMethod, endSrc, pushData);
            copyCtMethod.setBody(returnSrcCode);
        }
        //将修改后的方法添加到类里面
        ctClass.addMethod(copyCtMethod);
    }

    /**
     * 构建 适用于service的参数方法对象
     *
     * @param serviceStatisticsEntity 参数收集对象
     * @param className               类名称
     * @param simpleClassName         简单类名
     * @param methodName              方法名称
     * @param result                  结果集
     * @param paramArray              参数集合
     * @param returnType              返回值类型
     */
    public static void setServiceMethodData(ServiceStatisticsEntity serviceStatisticsEntity, String className, String simpleClassName, String methodName, Object result, Object[] paramArray, String returnType) {
        serviceStatisticsEntity.setArgs(paramArray);
        serviceStatisticsEntity.setClassName(className);
        serviceStatisticsEntity.setMethodName(methodName);
        serviceStatisticsEntity.setSimpleClassName(simpleClassName);
        serviceStatisticsEntity.setReturnType(returnType);
        serviceStatisticsEntity.setReturnValue(result);
    }

    @Override
    public boolean dataMatching(String verifyTheData) {
        boolean equals = "com.apm.UserService".equals(verifyTheData);
        return equals;
    }
}
