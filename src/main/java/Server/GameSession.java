package Server;

import Shared.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class GameSession {
    //private LinkedList<Socket> clients;
    //private LinkedList<Car> cars;
    private ArrayList<Obstacle> obstacles=new java.util.ArrayList<>();
    private LinkedList<Client> clients=new LinkedList<>();
    private LinkedList<Car> cars=new LinkedList<>();
    public GameSession(LinkedList<Socket> sockets){

        for (int i = 0; i < sockets.size(); i++) {
            Car car=new Car(10,100,100*(i+1));
            cars.add(car);
            clients.add(new Client(sockets.get(i),car));
        }
        loadLevel();
        start();
    }
    private void loadLevel(){
        try (InputStream is = new FileInputStream("maps/map");
             ObjectInputStream ois = new ObjectInputStream(is)) {
            obstacles=(ArrayList<Obstacle>) ois.readObject();

        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
            obstacles.add(new FinishLine(400,180,400,55));
        }
    }
    final int FPS=60;
    public void start(){
        long realDeltaTime=0;
        long lastUpdateTime=System.nanoTime();

        double targetFrameTime=1000000000/FPS;
        double accumulator=0;


        while (true){
            realDeltaTime=System.nanoTime()-lastUpdateTime;
            lastUpdateTime+=realDeltaTime;
            accumulator+=realDeltaTime;



            while (accumulator>targetFrameTime){
                update(targetFrameTime);// targetFrameTime or Accumulator????


                accumulator-=targetFrameTime;

            }




        }
    }

    private void handleClientMsg(Client client){
        //System.out.println("Server: handleClientMsg");
        ClientMsg clientMsg= null;
        try {
            clientMsg = (ClientMsg) client.in.readObject();
            //System.out.println("Server: object read");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (clientMsg.isUp()){
            client.getCar().gas();
        }
        if (clientMsg.isDown()){
            client.getCar().brake();
        }
        if (clientMsg.isLeft()){
            client.getCar().left();
        }
        if (clientMsg.isRight()){
            client.getCar().right();
        }





    }
    private void sendMsgToClient(Client client){
        //System.out.println("Server: sendMsgToClient");
        Socket socket=client.getSocket();
        LinkedList<CarView> carsView=new LinkedList<>();
        for (Car c:cars){
            carsView.add(new CarView(c.getPos(),c.getAlpha(),cars.indexOf(c)));
        }


            ServerMsg serverMsg=new ServerMsg(carsView);
        try {
            client.out.writeObject(serverMsg);
            //System.out.println("Server: object sent");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void update(double delta) {

        for (Client c: clients){
            handleClientMsg(c);
        }
        //carCollision(cars.get(0),cars.get(1));


        for(Car c:cars){
            c.friction();
            for (Obstacle o:obstacles){
                c.collision(o);

            }
            c.calcPos();
        }
        for (Client c: clients){
            sendMsgToClient(c);
        }
    }
    public boolean carCollision(Car c1, Car c2){
        Vector d1=new Vector(c2.getPos());
        d1.sub(c1.getPos());
        double dist=d1.mag();
        if (dist<20){
            dist=20-dist;
            dist*=0.5;

            d1.normalize();
            Vector d2=d1.copy();


            d1.mul(-dist);

            c1.setPos(c1.getPos().add(d1));
            //c1.pos.add(d1);
            c1.setVel(c1.getVel()*0.9);
            //c1.vel*=0.9;


            d2.mul(dist);
            c2.setPos(c2.getPos().add(d2));
            //c2.pos.add(d2);
            c2.setVel(c2.getVel()*0.9);
            //c2.vel*=0.9;




            return true;
        }
        return false;
    }
}
