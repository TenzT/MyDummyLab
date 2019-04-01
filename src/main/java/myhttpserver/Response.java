package myhttpserver;

import java.io.*;

public class Response {
    private static final int BUFFER_SIZE = 1024;
    private static final String HEADER_SUCCESS = "HTTP/1.1 200 OK\r\n" +
            "Server: Microsoft-IIS/4.0\r\n" +
            "Date: Mon, 5 Jan 2004 13:13:33 GMT\r\n" +
            "Content-Type: text/html\r\n" +
            "Last-Modified: Mon, 5 Jan 2004 13:13:12 GMT\r\n" +
            "Content-Length: 112\r\n" +
            "\r\n";

    private static final String HEADER_NOTFOUND = "HTTP/1.1 404 File Not Found\r\n" +
            "Content-Type: text/html\r\n" +
            "Content-Length: 23\r\n" +
            "\r\n" +
            "<h1>File Not Found</h1>";
    Request request;
    OutputStream output;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    // 返回静态资源
    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null; // 从文件来的输入流

        try {
            File file = new File(HttpServer.WEB_ROOT, request.getUri());
            if (file.exists()) {
                fis = new FileInputStream(file);
                // 头部信息作为流
                InputStream headerInput = new ByteArrayInputStream(HEADER_SUCCESS.getBytes());
                int ch = headerInput.read(bytes);
                ch += fis.read(bytes, ch, BUFFER_SIZE-ch);
                for (int i=0; i < ch; i++) {
                    System.out.print((char)bytes[i]);
                }


//                output.write(HEADER_SUCCESS.getBytes());
                while (ch!=-1) {
                    output.write(bytes, 0, ch);
                    /**
                     * off参数指的是byte中的位置
                     */
                    ch = fis.read(bytes, 0, BUFFER_SIZE);
                }
            } else {
                output.write(HEADER_NOTFOUND.getBytes());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }
}
