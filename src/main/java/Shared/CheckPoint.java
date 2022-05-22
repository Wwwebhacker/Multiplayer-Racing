package Shared;

import java.awt.*;

public class CheckPoint extends Obstacle {
    private int orderNumber;
    private boolean isFinishLine;
    private Color color;
    public CheckPoint(int x1, int y1, int x2, int y2,int orderNumber) {
        super(x1, y1, x2, y2);this.orderNumber=orderNumber;
        this.color=Color.ORANGE;
    }
    public CheckPoint(int x1, int y1, int x2, int y2,int orderNumber,Color color) {
        super(x1, y1, x2, y2);this.orderNumber=orderNumber;
        this.color=color;
    }



    @Override
    public void draw(Graphics g){

        g.setColor(color);
        g.drawLine(x1,y1,x2,y2);
    }



    public int getOrderNumber() {
        return orderNumber;
    }
}
