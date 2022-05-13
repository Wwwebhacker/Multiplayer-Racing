package Shared;

import java.awt.*;
import java.io.Serializable;

public class Obstacle implements Serializable {
    static final long serialVersionUID=1;
    protected int x1;
    protected int y1;
    protected int x2;
    protected int y2;

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }


    public Obstacle(int x1,int y1,int x2,int y2){
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
    }
    public void draw(Graphics g){
        g.setColor(Color.red);
        g.drawLine(x1,y1,x2,y2);
    }

    @Override
    public String toString() {
        return "Client.Obstacle{" +
                "x1=" + x1 +
                ", y1=" + y1 +
                ", x2=" + x2 +
                ", y2=" + y2 +
                '}';
    }
}
