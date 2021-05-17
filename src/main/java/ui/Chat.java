package ui;

import domain.Server;
import domain.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Chat implements Runnable {

    private final Socket socket;
    private final Server server;
    private final User user;
    private final PrintWriter writer;

    public Chat(Socket socket, Server server, User user) throws IOException {
        this.socket = socket;
        this.server = server;
        this.writer = new PrintWriter(socket.getOutputStream(), false);
        this.user = user;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public User getUser() {
        return user;
    }

    @Override
    public void run() {
        sendInitialGreeting();
        runChat();
        sendGoodbyeMessage();
    }

    private void sendInitialGreeting() {
        writer.write("Welcome to the chat! " + user.getName() + "\n");
        writer.write("People in the chat: " + "\n");
        server.getChats().forEach(chat -> writer.write(chat.getUser().getName() + "\n"));
        writer.flush();
    }

    private void sendGoodbyeMessage() {
        writer.write("Thank you for visiting the chat! \n");
        writer.write("Hope to you you again soon. \n");
        writer.flush();
    }

    private void runChat() {
        try (Scanner scanner = new Scanner(socket.getInputStream())) {
            String message = scanner.nextLine();
            do {
                for (Chat chat : server.getChats()) {
                    PrintWriter otherChatOut = chat.getWriter();
                    if (otherChatOut != null && chat != this) {
                        otherChatOut.write(user.getName() + ": " + message + "\n");
                        otherChatOut.flush();
                    }
                }
                message = scanner.nextLine();
            } while (!message.equals("stop"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
