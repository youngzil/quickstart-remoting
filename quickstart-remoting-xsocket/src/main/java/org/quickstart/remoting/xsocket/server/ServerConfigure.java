/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.quickstart.remoting.xsocket.server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lindily
 */
public class ServerConfigure {

    private String HOST;
    private int CLIENT_PORT;
    private String STORE_PATH;
    private String MAIL;
    private String MAIL_PASS;
    private int SERVER_PORT;
    private File confPath;
    private Properties prop=new Properties();

    public ServerConfigure() {
        confPath = new File("./servconf/configure.conf");
        try {
            if (!confPath.exists()) {
                confPath.createNewFile();
            }
            prop.load(new BufferedInputStream(new FileInputStream(confPath)));
        } catch (IOException ex) {
            Logger.getLogger(ServerConfigure.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the HOST
     */
    public String getHOST() {
        HOST = prop.getProperty("host", "localhost");
        return HOST;
    }

    /**
     * @param HOST the HOST to set
     */
    public void setHOST(String HOST) {
        prop.setProperty("host", HOST);
        this.HOST = HOST;
    }

    /**
     * @return the PORT
     */
    public int getCLIENT_PORT() {
        CLIENT_PORT = Integer.parseInt(prop.getProperty("client_port", "8314"));
        return CLIENT_PORT;
    }

    /**
     * @param PORT the PORT to set
     */
    public void setCLIENT_PORT(int PORT) {
        prop.setProperty("client_port", String.valueOf(PORT));
        this.CLIENT_PORT = PORT;
    }

    /**
     * @return the STORE_PATH
     */
    public String getSTORE_PATH() {
        STORE_PATH = prop.getProperty("store_path", "./cache");
        return STORE_PATH;
    }

    /**
     * @param STORE_PATH the STORE_PATH to set
     */
    public void setSTORE_PATH(String STORE_PATH) {
        prop.setProperty("store_path", STORE_PATH);
        this.STORE_PATH = STORE_PATH;
    }

    /**
     * @return the MAIL
     */
    public String getMAIL() {
        MAIL = prop.getProperty("mail", "mumaserv@126.com");
        return MAIL;
    }

    /**
     * @param MAIL the MAIL to set
     */
    public void setMAIL(String MAIL) {
        prop.setProperty("mail", MAIL);
        this.MAIL = MAIL;
    }

    /**
     * @return the MAIL_PASS
     */
    public String getMAIL_PASS() {
        MAIL_PASS = prop.getProperty("mail_pass", "mumaserver");
        return MAIL_PASS;
    }

    /**
     * @param MAIL_PASS the MAIL_PASS to set
     */
    public void setMAIL_PASS(String MAIL_PASS) {
        prop.setProperty("mail_pass", MAIL_PASS);
        this.MAIL_PASS = MAIL_PASS;
    }


    /**
     * @return the SERVER_PORT
     */
    public int getSERVER_PORT() {
        SERVER_PORT = Integer.parseInt(prop.getProperty("server_port", "8314"));
        return SERVER_PORT;
    }

    /**
     * @param SERVER_PORT the SERVER_PORT to set
     */
    public void setSERVER_PORT(int SERVER_PORT) {
        prop.setProperty("server_port", String.valueOf(SERVER_PORT));
        this.SERVER_PORT = SERVER_PORT;
    }
}
