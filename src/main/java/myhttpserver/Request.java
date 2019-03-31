package myhttpserver;

import java.io.IOException;
import java.io.InputStream;

public class Request {
    private InputStream input;
    private String uri;

    public String getUri() {
        return uri;
    }


    public Request(InputStream input) {
        this.input = input;
    }

    public void parse() {
        StringBuffer request = new StringBuffer();
        int i;
        // 用来接收缓存字节流
        byte[] buffer = new byte[2048];
        try {
            i = input.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1; // 接收失败
        }

        for (int j=0; j < i; j++) {
            request.append((char)buffer[j]);
        }
        System.out.println(request.toString());
        uri = parseUri(request.toString());
    }
    private String parseUri(String requestString) {
        int index1, index2;
        index1 = requestString.indexOf(" ");    // HTTP method之后的空格
        if (index1 != -1) {
            index2 = requestString.indexOf(" ", index1 + 1);    // uri之后的空格
            if (index2 > index1) {
                return requestString.substring(index1+1, index2);
            }
        }
        return null;
    }

}
