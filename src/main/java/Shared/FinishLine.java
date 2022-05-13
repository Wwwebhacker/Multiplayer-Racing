package Shared;

import Shared.Obstacle;

import java.awt.*;

public class FinishLine extends Obstacle {
    private double startTime=System.currentTimeMillis();
    private double lastLap=System.currentTimeMillis();
    public FinishLine(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
    }
    public void crossLine(){

        lastLap=System.currentTimeMillis()-startTime;
        startTime=System.currentTimeMillis();
    }

    public double getLastLap() {
        return lastLap/1000;
    }
    @Override
    public void draw(Graphics g){
        g.setColor(Color.BLUE);
        g.drawLine(x1,y1,x2,y2);
    }

    public void printTime(){
        if (lastLap>100){
            System.out.println(lastLap/1000);
        }


    }
}
