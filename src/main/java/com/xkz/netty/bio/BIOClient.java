package com.xkz.netty.bio;

import java.io.*;
import java.net.Socket;

/**
 *
 */
public class BIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 9999);
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        BufferedReader consoleInput = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            consoleInput = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String s = consoleInput.readLine();
                System.out.println(">>>" + s);
                if ("exit".equals(s)) {
                    break;
                }
                bufferedWriter.write(s);
                bufferedWriter.flush();
                String resp = bufferedReader.readLine();
                System.out.println("服务器返回：" + resp);
            }
        } catch (Exception e) {
            System.out.println("客户端异常");
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (bufferedWriter != null) {
                    bufferedReader.close();
                }
                if (consoleInput != null) {
                    consoleInput.close();
                }
            } catch (Exception ex) {
                System.out.println("关闭资源失败");
            }

        }

    }
}
