package Client;

import Shared.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class Panel extends JPanel implements Runnable{

    Socket client;
    ObjectInputStream in;
    ObjectOutputStream out;

    KeyHandler keyH=new KeyHandler();
    Mouse mListner=new Mouse();

    final int FPS=60;

    LinkedList<CarView> carsView;
    ArrayList<Obstacle> obstacles=new ArrayList<>();
    public Panel() {
        this.setPreferredSize(new Dimension(1200,800));
        this.setBackground(Color.black);
        //this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        addMouseListener(mListner);
        addMouseMotionListener(mListner);
        this.setFocusable(true);

        startGame();
    }
    void connect()  {
        try {
            client = new Socket("localhost", 9191);
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());

            System.out.println("Client: connected to"+client);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendMsgToClient(new ClientMsg());
    }
    Thread gameThread;
    private void loadLevel(){
        try (InputStream is = new FileInputStream("maps/map");
             ObjectInputStream ois = new ObjectInputStream(is)) {
            obstacles=(ArrayList<Obstacle>)ois.readObject();

        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
            obstacles.add(new CheckPoint(400,180,400,55,0,Color.BLUE));
            obstacles.add(new CheckPoint(595,643,594,782,1));
            obstacles.add(new CheckPoint(612,195,611,378,2));

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
    private void startGame()  {
        connect();
        gameThread=new Thread(this);
        loadLevel();
        gameThread.start();
    }
    @Override
    public void run() {

        long realDeltaTime=0;
        long lastUpdateTime=System.nanoTime();

        double targetFrameTime=1000000000.0/FPS;
        double accumulator=0;

        while (!gameThread.isInterrupted()){
            realDeltaTime=System.nanoTime()-lastUpdateTime;
            lastUpdateTime+=realDeltaTime;
            accumulator+=realDeltaTime;
            while (accumulator>targetFrameTime){
                try {
                    update(targetFrameTime);// targetFrameTime or Accumulator????
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                accumulator-=targetFrameTime;
            }
            repaint();
        }
    }

    void showMsg(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString(data,10,20);
    }

    private String data="";
    void handleServerMsg(){
        System.out.println("handleServerMsg start"+client);
        ServerMsg serverMsg= null;
        try {
            serverMsg = (ServerMsg) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        carsView=serverMsg.getCars();
        data=serverMsg.getMsg();
        System.out.println("handleServerMsg end"+client);
    }

    private void sendMsgToClient(ClientMsg clientMsg){
        System.out.println("sendMsgToClient start"+client);
        try {
            out.writeObject(clientMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("sendMsgToClient end"+client);
    }

    private void update(double delta) throws InterruptedException {
        handleServerMsg();
        ClientMsg clientMsg=new ClientMsg();
        if (keyH.up){
            clientMsg.setUp(true);
        }
        if (keyH.left){
            clientMsg.setLeft(true);
        }
        if (keyH.right){
            clientMsg.setRight(true);
        }
        if (keyH.down){
            clientMsg.setDown(true);
        }
        sendMsgToClient(clientMsg);
    }
    public void draw(Graphics g, CarView carView){
        Color color=carView.getColor();
        Vector pos=carView.getPos();
        double alpha=carView.getHeading();

        double height=15;
        double width=30;


        int x1=(int) pos.x;
        int y1=(int) pos.y;

        Vector center=new Vector(x1,y1);
        //TOP RIGHT VERTEX:
        Vector Top_Right=new Vector(0,0);
        double angle=alpha*Math.PI/180;
        Top_Right.x = center.x + ((width / 2) * Math.cos(angle)) - ((height / 2) * Math.sin(angle));
        Top_Right.y = center.y + ((width / 2) * Math.sin(angle)) + ((height / 2) * Math.cos(angle));

        //TOP LEFT VERTEX:
        Vector Top_Left=new Vector(0,0);
        Top_Left.x = center.x - ((width / 2) * Math.cos(angle)) - ((height / 2) * Math.sin(angle));
        Top_Left.y = center.y - ((width / 2) * Math.sin(angle)) + ((height / 2) * Math.cos(angle));

        // LEFT VERTEX:
        Vector Bot_Left=new Vector(0,0);
        Bot_Left.x = center.x - ((width / 2) * Math.cos(angle)) + ((height / 2) * Math.sin(angle));
        Bot_Left.y = center.y - ((width / 2) * Math.sin(angle)) - ((height / 2) * Math.cos(angle));

        //BOTTOM RIGHT VERTEX:
        Vector Bot_Right=new Vector(0,0);
        Bot_Right.x = center.x + ((width / 2) * Math.cos(angle)) + ((height / 2) * Math.sin(angle));
        Bot_Right.y = center.y + ((width / 2) * Math.sin(angle)) - ((height / 2) * Math.cos(angle));

        g.setColor(color);
        g.drawLine((int)Top_Left.x,(int)Top_Left.y,(int)Top_Right.x,(int)Top_Right.y);
        g.drawLine((int)Bot_Left.x,(int)Bot_Left.y,(int)Bot_Right.x,(int)Bot_Right.y);

        g.drawLine((int)Top_Left.x,(int)Top_Left.y,(int)Bot_Left.x,(int)Bot_Left.y);
        g.drawLine((int)Top_Right.x,(int)Top_Right.y,(int)Bot_Right.x,(int)Bot_Right.y);

        g.setColor(Color.WHITE);
        g.drawArc((int)(Bot_Right.x-2.5),(int)(Bot_Right.y-2.5),5,5,0,360);
        g.drawArc((int)(Top_Right.x-2.5),(int)(Top_Right.y-2.5),5,5,0,360);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (carsView!=null){
            for(CarView cV:carsView){
                draw(g,cV);
            }
        }
        for (Obstacle o:obstacles){
            o.draw(g);
        }
        showMsg(g);
    }


}
