package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ChatHandler implements Runnable {

    private PrintWriter writer;
    private BufferedReader reader;
    private String username;
    private Socket socket;
    ArrayList<String> usernames = new ArrayList<>();
    private static ArrayList<PrintWriter> userWriters = new ArrayList<>();

    @Override
    public void run() {

        try {
            this.writer = new PrintWriter(socket.getOutputStream(), true);
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.username = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("User " + username + " connected");
        synchronized (usernames){
            usernames.add(username);
        }
        userWriters.add(writer);
        while (true){
            String input = null;
            while(true){
                try {
                    if (!((input = reader.readLine()) != null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                    for (PrintWriter printWriter : userWriters){
                        printWriter.println(username  + " : " + input);
                    }
            }
        }
    }

    public ChatHandler(Socket socket) throws IOException {
        this.socket = socket;
    }
}
