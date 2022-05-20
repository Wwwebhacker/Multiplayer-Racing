package Shared;

import java.awt.*;
import java.io.Serializable;

public class CarView implements Serializable {
    private Vector pos;
    private double heading;
    private int id;
    private Color color;
    static final long serialVersionUID=1;
    public CarView(Vector pos, double heading, int id, Color color){
        this.pos=pos;
        this.heading=heading;
        this.id=id;
        this.color=color;
    }

    public Color getColor() {
        return color;
    }

    public Vector getPos() {
        return pos;
    }

    public int getId() {
        return id;
    }

    public double getHeading() {
        return heading;
    }
}
