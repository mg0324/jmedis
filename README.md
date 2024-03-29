# jmedis
java版自实现redis

## 快速使用
### jmedis-server
进入bin目录，执行如下命令启动服务端
``` bash
./jmedis-server.sh jmedis.conf &
```

### jmedis-client
进入bin目录，执行如下命令启动客户端
``` bash
./jmedis-cli.sh -h 192.168.0.1
```
或者使用[rlwrap](https://github.com/hanslub42/rlwrap)来增强bash，支持上下左右箭头的功能。
```shell
rlwrap ./jmedis-cli.sh -h 192.168.0.1
```

## 实现功能及优化点
### jmedis-server
1. 基于nio实现多路复用器，单线程支持多个客户端
2. 实现ping pong测试命令
3. 加入db=16 下标支持
4. 实现set命令
5. 实现get命令
6. 实现select命令
7. 基于模板方法设计模式，抽象封装命令的校验
8. 实现keys命令
9. 实现exit命令
10. ~~优化命令注册代码，基于`reflections`加`@Cmd`注解实现自动注册~~
11. 去掉`reflections`框架，利用反射代码解析`@Cmd`实现自动注册，减小服务端程序jar包容量
12. 只引入`logback`做日志框架，去掉`lombok`,缩小服务端程序大小到842K
13. 优化代码，修复服务器异常退出的问题
14. 加入配置文件加载功能，有限读取配置文件值，没有则使用程序默认值。
15. 实现密码认证及auth命令。
16. 引入责任链模式，优化命令执行前的校验逻辑。
17. 利用`@NoAuth`注解实现命令白名单。
18. 实现echo命令。
19. 实现dbsize命令。
20. 实现time命令。
21. 实现info命令，搭建好基本架构，基于`sigar`工具包。
``` bash
127.0.0.1:8000[0]>info
# Server
tcp_port=8000
process_id=64187
jmedis_dbsize=16
os=Mac OS X x86_64 10.15.7
jmedis_version=1.0.0
java_version=1.8.0_221
jmedis_buffer_size=4096KB
config_file=/Users/mango/git/jmedis/jmedis-server/src/main/bin/jmedis.conf
uptime=2542583.0

# Clients
connected_clients=1
```
22. 实现flushdb命令。
23. 实现flushall命令。
24. 实现字符串的append命令。
25. 实现expire命令。
26. 实现ttl命令。
27. 实现定时任务`ExpireTask`处理过期`key`。
28. 任务执行时加入命令后置处理器钩子。
29. 实现执行带`@WithExpire`注解的命令后，渐进式处理当前数据库里的key的过期时间。


### jmedis-client
1. 基于apache commons cli框架实现java cmd程序
2. 基于socketChannel阻塞模式，实现jmedis client客户端
3. 使用责任链模式处理客户端参数
4. 使用责任链模式处理客户端到服务端命令执行
5. 客户端支持-password预设置密码认证