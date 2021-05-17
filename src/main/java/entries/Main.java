package entries;

import domain.Server;

public class Main {

    public static void main(String[] args) {
        Server server = new Server(4444);
        server.startServer();
    }

}
