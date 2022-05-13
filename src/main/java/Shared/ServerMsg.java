package Shared;

import java.io.Serializable;
import java.util.LinkedList;

public class ServerMsg implements Serializable {
    static final long serialVersionUID=1;
    private LinkedList<CarView> cars;

    public ServerMsg(LinkedList<CarView> cars){
        this.cars=cars;
    }

    public LinkedList<CarView> getCars() {
        return cars;
    }
}
