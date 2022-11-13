package com.xkz.netty.bio;


import lombok.Data;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Data
public class BIOService {
    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        ServerSocket ss = new ServerSocket(9999);

        while (true) {
            Socket accept = ss.accept();
            System.out.println(Thread.currentThread().getName());
            executorService.execute(() -> {
                handler(accept);
            });
        }
    }

    public static void handler(Socket accept) {
        byte[] bytes = new byte[1024];
        InputStream inputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            while (true) {
                inputStream = accept.getInputStream();
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                }
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(accept.getOutputStream()));
                bufferedWriter.write("ok");
                bufferedWriter.flush();
            }
        } catch (Exception e) {
            System.out.println("服务器异常");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (Exception e) {
                System.out.println("关闭资源失败！");
            }
        }

    }
}
