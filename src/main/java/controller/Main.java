package controller;

import domain.Server;
import ui.Chat;

public class Main {

    public static void main(String[] args) {
        Server server = new Server(4444);
        server.startServer();
    }

}
