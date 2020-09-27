package com.apm.constants;

/**
 * 统计类模板常量
 *
 * @author huangfu
 */
public class StatisticsClassTemplateConstants {
    /**
     * 无参返回值的方法模板
     */
    public static final String VOID_METHOD_TYPE_TEMPLATE = "{\n" +
            "%s;\n" +
            "%s;\n" +
            "try {\n" +
            "\t%s($$);\n" +
            "}catch (Exception e) {\n" +
            "\t%s;\n" +
            "\tthrow e;\n" +
            "}finally {\n" +
            "\t%s;\n" +
            "\t%s;\n" +
            "\t%s;\n" +
            "}\n" +
            "}";

    /**
     * 有参返回值的方法模板
     */
    public static String RETURN_METHOD_TYPE_TEMPLATE = "{\n" +
            "%s;\n" +
            "%s;" +
            "Object result$ApmAgent = null;\n" +
            "try{\n" +
            "\tresult$ApmAgent = ($w)%s($$);\n" +
            "}catch (Exception e) {\n" +
            "\t%s;\n" +
            "\tthrow e;\n" +
            "}finally {\n" +
            "\t%s;\n" +
            "\t%s;\n" +
            "\t%s;\n" +
            "}\n" +
            "return ($r) result$ApmAgent;\n" +
            "}\n";

}
