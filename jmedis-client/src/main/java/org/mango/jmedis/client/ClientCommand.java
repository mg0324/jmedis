package org.mango.jmedis.client;

import org.apache.commons.cli.*;
import org.mango.jmedis.handler.cmd.CmdHandler;
import org.mango.jmedis.handler.cmd.impl.HelpCmdHandler;
import org.mango.jmedis.handler.cmd.impl.MainCmdHandler;
import org.mango.jmedis.handler.cmd.impl.PasswordCmdHandler;
import org.mango.jmedis.handler.cmd.impl.VersionCmdHandler;
import org.mango.jmedis.util.ClientUtil;

/**
 * @Description 客户端命令行
 * @Date 2021-10-24 14:28
 * @Created by mango
 */
public class ClientCommand {
    // 可选选项集
    public static Options options;
    // 处理器 chain
    private CmdHandler cmdHandler;

    public ClientCommand(){
        this.options = new Options();
        this.init();
        this.initHandler();
    }
    // 初始化选项
    private void init(){
        // 添加一个选项 c ，并加上对应的简短说明，第二个参数表明这个选项是否跟有参数
        options.addOption("h", true, "jmedis server host, eg:127.0.0.1");
        options.addOption("p", true, "jmedis server port, eg:8000");
        options.addOption("password", true, "jmedis server auth password");
        options.addOption("help", "show jmedis-cli help");
        options.addOption("version", "show jmedis-cli version, eg:1.0.0");
    }
    // 初始化处理器列表
    private void initHandler(){
        // 1 help
        cmdHandler = new HelpCmdHandler();
        // 2 version
        CmdHandler versionCmdHandler = new VersionCmdHandler();
        cmdHandler.setNext(versionCmdHandler);// set 1'next = 2
        // 3 password
        CmdHandler passwordCmdHandler = new PasswordCmdHandler();
        versionCmdHandler.setNext(passwordCmdHandler);
        // 100 main
        CmdHandler mainCmdHandler = new MainCmdHandler();
        passwordCmdHandler.setNext(mainCmdHandler);
    }

    /**
     * 处理命令
     * @param args
     */
    public void handle(String[] args){
        // 初始化一个命令行解析器，一般用默认的就可以
        CommandLineParser parser = new DefaultParser();
        try {
            // 解析后会得到一个 CommandLine 对象
            CommandLine cmd = parser.parse(options, args);
            // 开始链式处理命令行
            this.cmdHandler.handle(cmd);
        } catch (ParseException e) {
            // 解析失败是用 HelpFormatter 打印 帮助信息
            ClientUtil.showUsage(options);
        }
    }
}
