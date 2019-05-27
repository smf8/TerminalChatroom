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
            try {
                this.username = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("User " + username + " connected");
            synchronized (usernames) {
                usernames.add(username);
            }
            userWriters.add(writer);
            while (true) {
                String input = null;
                while (true) {
                    try {
                        if (!((input = reader.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (input.equals("--exit--")){
                        System.out.println("--exit-- " +username);
//                        socket.close();
//                        reader.close();
//                        writer.close();
//                        userWriters.remove(writer);
                        return;
                    }
                    for (PrintWriter printWriter : userWriters) {
                        printWriter.println(username + " : " + input);
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (writer != null)
                userWriters.remove(writer);
            System.out.println("Client quited :" +username);
            System.out.println(userWriters.size() + " " + usernames.size());
            for (PrintWriter printWriter : userWriters) {
                printWriter.println("--" + username + "--" + " has left");
            }
                try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ChatHandler(Socket socket) throws IOException {
        this.socket = socket;
    }
}
