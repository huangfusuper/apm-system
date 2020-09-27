# apm-system

## 介绍
>代码零侵入监控技术

## 基础使用

jvm参数增加指令：
```shell script
-javaagent:apm.jar路径=期望参数（String）
```
项目内依赖：
```xml
    <dependency>
      <groupId>org.javassist</groupId>
      <artifactId>javassist</artifactId>
      <version>${javassist-version}</version>
    </dependency>
```