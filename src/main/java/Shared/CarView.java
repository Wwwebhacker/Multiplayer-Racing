package Shared;

import java.io.Serializable;

public class CarView implements Serializable {
    private Vector pos;
    private double heading;
    private int id;
    static final long serialVersionUID=1;
    public CarView(Vector pos, double heading, int id){
        this.pos=pos;
        this.heading=heading;
        this.id=id;
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
