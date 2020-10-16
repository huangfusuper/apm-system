package com.apm.trans;

import com.apm.constants.StatisticsClassTemplateConstants;
import com.apm.entity.ServiceStatisticsEntity;
import com.apm.utils.JavaAgentPropertiesKey;
import com.apm.utils.JavaAgentPropertiesUtil;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

/**
 * 业务类转换器
 *
 * @author huangfu
 */
public class ServiceClassFileTransformer extends BaseClassFileTransformer {

    public static final String VOID_TYPE_NAME = "void";

    /**
     * 构建统计方法对象
     *
     * @param ctClass  类对象
     * @param ctMethod 方法对象
     */
    @Override
    public void buildMethod(CtClass ctClass, CtMethod ctMethod) throws Exception {
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
        String basePathStr = JavaAgentPropertiesUtil.getConfig(JavaAgentPropertiesKey.FILTER_SERVICE_PACKAGE);
        return verifyTheData.contains(basePathStr);
    }
}
