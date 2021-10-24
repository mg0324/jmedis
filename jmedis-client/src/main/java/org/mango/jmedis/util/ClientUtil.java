package org.mango.jmedis.util;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.mango.jmedis.client.JMedisClient;
import org.mango.jmedis.config.ClientConf;

/**
 * @Description 客户端工具
 * @Date 2021-10-24 14:25
 * @Created by mango
 */
public class ClientUtil {
    /**
     * 帮助格式化器
     */
    private static final HelpFormatter helpFormatter = new HelpFormatter();

    /**
     * 获取格式化器
     * @return
     */
    public static HelpFormatter getHelpFormatter(){
       return helpFormatter;
    }

    /**
     * 打印usage
     * @param options
     */
    public static void showUsage(Options options){
        helpFormatter.printHelp(ClientConf.PROGRAM_NAME + " [options]", options);
    }

    /**
     * 返回数据给控制台
     * @param data
     */
    public static void print(String data){
        System.out.print(data);
    }

    /**
     * 返回数据给控制台
     * @param data
     */
    public static void println(String data){
        System.out.println(data);
    }

    /**
     * 显示commandLine
     */
    public static void showCmdLine(JMedisClient client) {
        String line = client.getHost() + ":" + client.getPort() + "[" + client.getDbIndex() + "]>";
        System.out.print(line);
    }
}
