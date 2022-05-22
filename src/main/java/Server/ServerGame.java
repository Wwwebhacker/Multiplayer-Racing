package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class ServerGame {
    public static void main(String[] args) {
        LinkedList<Socket> clients=new LinkedList<>();
        try (ServerSocket server = new ServerSocket(9797)) {
            for (int i = 0; i < 3; i++) {

                Socket socket = server.accept();
                System.out.println("New client connected: "+socket);
                clients.add(socket);
            }
            GameSession gameSession=new GameSession(clients);
            gameSession.start();

        } catch (IOException ex) {
            System.err.println(ex);
        }

    }
}
