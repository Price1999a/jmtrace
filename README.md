# JMTrace

## 编译需要
JDK >= 1.8  
maven 3.6.3 （程序开发基于此版本maven）

## 编译说明

- 确保可靠的互联网连接，程序依靠maven管理java库依赖。
- 在程序源代码根目录下，运行 `mvn package` 命令。
- `./target/jmtrace-1.0-jar-with-dependencies.jar` 就是目标程序。

## 运行工具简要说明

程序必须在编译后才能运行。

在程序源代码根目录下 运行 `./jmtrace {args}`  
参数格式与`java`命令类似。  


### 参考资料  

- asm框架文档
- tamiflex：一个参考工具
- 个人毕业设计
- jvm字节码手册
- https://stackoverflow.com/questions/57398474/is-there-a-way-to-swap-long-or-double-and-reference-values-on-jvm-stack
