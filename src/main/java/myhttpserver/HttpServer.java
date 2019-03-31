package myhttpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    // WEB_ROOT文件目录
    public static final String WEB_ROOT = "target/classes/myhttpserver/WEB_ROOT";

    // 关停指令
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    // 是否收到关停指令
    private boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.await();
    }

    // 核心函数
    public void await() {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            /**
             * 第一个参数是端口，第二个参数是队列中最大的等待连接数
             */
            serverSocket = new ServerSocket(port, 1,
                    InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // 一直循环
        while (!shutdown) {
            Socket socket = null;   // 接收监听到的连接
            InputStream input = null;
            OutputStream output = null;

            try {
                socket = serverSocket.accept();
                input = socket.getInputStream();
                output = socket.getOutputStream();

                // 创建Request对象
                Request request = new Request(input);
                request.parse();

                // 创建Response对象
                Response response = new Response(output);
                response.setRequest(request);
                response.sendStaticResource();

                // Close the socket
                socket.close();
                //check if the previous URI is a shutdown command
                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

        }
    }
}
