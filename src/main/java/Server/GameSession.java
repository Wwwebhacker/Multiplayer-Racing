package Server;

import Shared.*;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class GameSession {

    private ArrayList<Obstacle> obstacles=new java.util.ArrayList<>();
    private LinkedList<Client> clients=new LinkedList<>();
    private ArrayList<Car> cars=new ArrayList<>();

    private Color pickColor(int i){
        switch (i){
            case 0:
                return Color.CYAN;
            case 1:
                return Color.GREEN;
            case 2:
                return Color.ORANGE;
            default:
                return Color.RED;
        }
    }

    public GameSession(LinkedList<Socket> sockets){

        for (int i = 0; i < sockets.size(); i++) {
            Car car=new Car(10,350,80+25*i,pickColor(i));
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

            obstacles.add(new CheckPoint(400,180,400,55,0));
            obstacles.add(new CheckPoint(595,643,594,782,1));
            obstacles.add(new CheckPoint(612,195,611,378,2));

            RaceProgress.setNumberOfCheckpoints(3);

            obstacles.add(new Obstacle(400,189,937,194));
            obstacles.add(new Obstacle(937,194,1029,284));
            obstacles.add(new Obstacle(1029,284,1020,641));
            obstacles.add(new Obstacle(1020,641,259,644));
            obstacles.add(new Obstacle(400,48,942,66));
            obstacles.add(new Obstacle(942,66,1155,162));
            obstacles.add(new Obstacle(1155,162,1170,784));
            obstacles.add(new Obstacle(1170,784,26,781));
            obstacles.add(new Obstacle(26,781,30,499));
            obstacles.add(new Obstacle(30,499,738,509));
            obstacles.add(new Obstacle(738,510,883,475));
            obstacles.add(new Obstacle(883,475,887,394));
            obstacles.add(new Obstacle(887,394,580,378));
            obstacles.add(new Obstacle(399,190,393,379));
            obstacles.add(new Obstacle(393,379,191,378));
            obstacles.add(new Obstacle(29,499,37,42));
            obstacles.add(new Obstacle(37,42,400,47));
            obstacles.add(new Obstacle(190,375,191,188));
        }
    }

    final int FPS=60;
    public void start(){
        long realDeltaTime=0;
        long lastUpdateTime=System.nanoTime();

        double targetFrameTime=1000000000.0/FPS;
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
        ClientMsg clientMsg= null;
        try {
            clientMsg = (ClientMsg) client.in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (client.getCar().getRaceProgress().getRaceResults()!=0){
            return;
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

    private void sendMsgToClient(Client client, String msg){

        Socket socket=client.getSocket();
        LinkedList<CarView> carsView=new LinkedList<>();
        for (Car c:cars){
            carsView.add(new CarView(c.getPos(),c.getAlpha(),cars.indexOf(c), c.getColor()));
        }

        ServerMsg serverMsg=new ServerMsg(carsView, msg);

        if (msg.equals("")){
            serverMsg=new ServerMsg(carsView, client.getCar().getRaceProgress().getProgressMsg());
        }


        try {
            client.out.writeObject(serverMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int numberOfFinishers=0;

    private void moveOutOfBounds(Car c){

        c.setPos(new Vector(957+c.getRaceProgress().getRaceResults()*25,27));
        c.setVel(0);
        c.setAlpha(90);
    }

    private void update(double delta) {


        for (Client c: clients){
            handleClientMsg(c);
        }

        for (int i=0;i<cars.size();i++){
            for (int j=i+1;j<cars.size();j++){
                Car c1= cars.get(i);
                Car c2=cars.get(j);
                carCollision(c1,c2);
            }
        }



        for(Car c:cars){
            c.friction();
            for (Obstacle o:obstacles){
                c.collision(o);

            }
            c.calcPos();
        }



        for (Client c: clients){
            RaceProgress raceProgress=c.getCar().getRaceProgress();
            if (raceProgress.checkIfEnd()){
                if (raceProgress.getRaceResults()==0){
                    numberOfFinishers++;
                    raceProgress.setRaceResults(numberOfFinishers);
                    moveOutOfBounds(c.getCar());
                }

                sendMsgToClient(c,"Your place: "+raceProgress.getRaceResults());
            }else {
                sendMsgToClient(c,"");
            }

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

            c1.setVel(c1.getVel()*0.9);



            d2.mul(dist);
            c2.setPos(c2.getPos().add(d2));

            c2.setVel(c2.getVel()*0.9);
            return true;
        }
        return false;
    }
}
