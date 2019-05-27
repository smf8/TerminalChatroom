package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8585);
        Scanner localReader = new Scanner(System.in);
        System.out.print("Enter name : ");
        String name = localReader.next();

        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.println(name);
        while(!name.equals("/exit")){
            writer.println(name);
        }
        System.out.println("Exitting...");
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        new Thread(new Runnable() {
            @Override
            public void run() {
                String input = "";
                while (true){
                    try {
                        if (!((input = reader.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(input);
                }
            }
        }).start();
    }
}
