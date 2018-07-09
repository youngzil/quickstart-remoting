/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.quickstart.remoting.xsocket.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lindily
 */
public class CmdExecuter {

    String cmd = "";
    private StringBuffer result = new StringBuffer();
    Scanner scanner = new Scanner(System.in);

    public CmdExecuter(String cmd) {
        this.cmd = cmd;
        String os = System.getProperty("os.name");
        System.out.println("os="+os);
        try {
            if (os.matches(".*[W|w]indows.*")) {
                this.cmd = new String("cmd /c " + cmd.trim());
            } else if (os.matches(".*[L|l]inux.*")) {
                this.cmd = new String("/bin/sh -c " + cmd.trim());
            }
            Runtime rt = Runtime.getRuntime(); // 获取运行时系统
            Process proc = rt.exec(cmd); // 执行命令
            BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(proc.getInputStream()), "gbk"));
            String res = null;
            while ((res = br.readLine()) != null) {
                result.append(res + "\r\n");
            }
//这里最终得改为客户端回显发送结构                
//System.out.println(result);
        } catch (IOException ex) {
            Logger.getLogger(CmdExecuter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /**
     * @return the result
     */
    public String getResult() {
        return result.toString();
    }

    //test main
    public static void main(String args[]) {
        CmdExecuter ce = new CmdExecuter("dir c:");
        System.out.println(ce.getResult());
    }
}
