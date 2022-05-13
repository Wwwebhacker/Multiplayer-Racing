package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private Socket socket;
    private Car car;
    ObjectInputStream in;
    ObjectOutputStream out;
    public Client(Socket s,Car c){
        this.socket=s;
        this.car=c;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Car getCar() {
        return car;
    }

    public Socket getSocket() {
        return socket;
    }
}
