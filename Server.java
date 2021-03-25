package chatApps;

import java.net.*;
import java.util.*;
import java.io.*;

public class Server
{
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter pr;
    public Server()
    {
        try {
            server = new ServerSocket(7777);
            System.out.println("server is ready to accept connection.");
            System.out.println("waiting...");
            socket = server.accept();
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pr = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void startReading() {
        Runnable r1 = () -> {
            System.out.println("reader started..");
            try {
                while (true) {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Client terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("Client : " + msg);
                }
            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("connection closed.");
            }
        };
        new Thread(r1).start();
    }
    public void startWriting()
    {
        Runnable r2=()->{
            System.out.println("Writer started.");
            try {
                while (!socket.isClosed()) {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    pr.println(content);
                    pr.flush();
                    if (content.equals("exit"))
                    {
                        socket.close();
                        break;
                    }
                }
            }
            catch (Exception e)
            {
                // e.printStackTrace();
                System.out.println("connection closed.");
            }
        };
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("this is server..going to start server");
        new Server();
    }
}
