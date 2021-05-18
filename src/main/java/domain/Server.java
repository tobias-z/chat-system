package domain;

import domain.user.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ui.Chat;

public class Server {

    private final int portNumber;
    private final List<Chat> chats;

    public Server(int portNumber) {
        this.portNumber = portNumber;
        this.chats = new ArrayList<>();
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server started at port: " + portNumber);
            acceptClients(serverSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void acceptClients(ServerSocket serverSocket) {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Accepted: " + socket.getRemoteSocketAddress());
                User user = getUserInformation(socket);
                Chat chat = new Chat(socket, this, user);
                Thread thread = new Thread(chat);
                thread.start();
                chats.add(chat);
            } catch (IOException e) {
                System.out.println("Unable to accept on port: " + portNumber);
            }
        }
    }

    private User getUserInformation(Socket socket) {
        try {
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), false);
            writer.write("What is your name? \n");
            writer.flush();
            return new User(scanner.nextLine());
        } catch (IOException e) {
            return new User("Unknown");
        }
    }

}
