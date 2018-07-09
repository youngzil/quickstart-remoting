/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.quickstart.remoting.xsocket.client;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lindily
 */
public class CmdInput implements Runnable {

    String cmd;
    Scanner input = null;

    public void input() {
        ClientConnection client = new ClientConnection();
        System.out.print("$");
        input = new Scanner(System.in);
        cmd = input.nextLine();
        while (!cmd.equals("exit")) {
            if (!cmd.equals("\r\n")) {
                try {
                    client.nonBlockingConnect();
                    //向服务端写数据信息    
                    client.getNbc().write(cmd + " \r\n");
                    client.getNbc().flush();
                } catch (IOException ex) {
                    Logger.getLogger(CmdInput.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BufferOverflowException ex) {
                    Logger.getLogger(CmdInput.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.print("$");
            input = new Scanner(System.in);
            cmd = input.nextLine();
        }
    }

    @Override
    public void run() {
        input();
    }
}
