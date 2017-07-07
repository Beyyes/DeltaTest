#使用说明


本教程将介绍如何将JDBC包安装到本地，并让maven项目对其依赖

首先去Tower-Tsfile项目的文件中心下载tsfile-jdbc.jar

之后执行下面的步骤

##步骤一
安装jar包，命令如下

```
mvn install:install-file -DgroupId=com.corp.tsfile -DartifactId=jdbc -Dversion     =0.0.2 -Dpackaging=jar -Dfile={FilePath}
```

其中{FilePath}为jar包所在的本地l路径

##步骤二

在maven项目的pom.xml文件中，加入下面的依赖

```
<dependency>	<groupId>com.corp.tsfile</groupId>	<artifactId>jdbc</artifactId>	<version>0.0.2</version></dependency>
```

##步骤三

enjoy it!