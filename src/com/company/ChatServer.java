package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class ChatServer {
        private static final int PORT = 8585;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8585);
        var threadPool = Executors.newFixedThreadPool(10);
        while(true){
            threadPool.execute(new ChatHandler(serverSocket.accept()));
        }
    }
}
