package com.version.common.util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @Author 周希来
 * @Date 2019/9/30 16:05
 */
public class ShUtil {
    private Connection conn;
    private String ipAddr;
    private String charset = Charset.defaultCharset().toString();
    private String userName;
    private String password;



    public ShUtil(String ipAddr, String userName, String password,
                  String charset) {
        this.ipAddr = ipAddr;
        this.userName = userName;
        this.password = password;
        if (charset != null) {
            this.charset = charset;
        }
    }

    public static  String exec(String ipAddr, String userName, String password,String sh){
        ShUtil tool = new ShUtil(ipAddr, userName,
                password , "utf-8");

        return tool.exec(sh);

    }

    public boolean login() throws IOException {
        conn = new Connection(ipAddr);
        conn.connect(); // 连接
        return conn.authenticateWithPassword(userName, password); // 认证
    }

    public String exec(String cmds) {
        InputStream in = null;
        String result = "";
        Session session = null;
        try {
            if (this.login()) {
                // 打开一个会话
                session = conn.openSession();
                session.execCommand(cmds);

                in = session.getStdout();
                result = this.processStdout(in, this.charset);
                session.close();
                conn.close();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }finally {
            if(session != null) {
                session.close();
            }
            conn.close();
        }
        return result;
    }

    public String processStdout(InputStream in, String charset) {

        byte[] buf = new byte[1024];
        StringBuffer sb = new StringBuffer();
        try {
            while (in.read(buf) != -1) {
                sb.append(new String(buf, charset));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    /**
     * @param args
     */
    public static void main(String[] args) {

        ShUtil tool = new ShUtil("114.55.92.111", "root",
                "742042@Mn", "utf-8");

        String result = tool.exec("./check.sh");
        System.out.print(result);

    }

}
