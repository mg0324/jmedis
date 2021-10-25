# jmedis
java版自实现redis

# 实现功能
## jmedis-server
1. 基于nio实现多路复用器，单线程支持多个客户端
2. 实现ping pong测试命令
3. 加入db=16 下标支持
4. 实现set命令
5. 实现get命令
6. 实现select命令
7. 基于模板方法设计模式，抽象封装命令的校验
8. 实现keys命令
9. 实现exit命令
10. 优化命令注册代码，基于`reflections`加`@Cmd`注解实现自动注册

## jmedis-client
1. 基于apache commons cli框架实现java cmd程序
2. 基于socketChannel阻塞模式，实现jmedis client客户端
3. 使用责任链模式处理客户端参数
4. 使用责任链模式处理客户端到服务端命令执行