# JmTrace

状态： 正在开发 主要利用soufflé跑的时间忙里偷闲，稍微写写  
考虑到下周任务，xdm肝吧 

## 预计使用技术
基于asm框架以及javaagent 对载入的目标字节码进行修改。
### 预计参考资料  

- asm框架文档
- tamiflex：一个参考工具
- 个人毕业设计
- jvm字节码手册
- https://stackoverflow.com/questions/57398474/is-there-a-way-to-swap-long-or-double-and-reference-values-on-jvm-stack

### 预计所需时间
10h - 1h - 5h - 3h

主要难点不在asm或者javaagent上 主要难点在jvm的内存模型上

把大框架大概搭了一下，主要问题在两项  
asm 插桩  
记录函数  
剩余任务：
- 文档写作

链接  
https://github.com/Price1999a/jmtrace/invitations