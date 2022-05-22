package Shared;

import java.io.Serializable;
import java.util.LinkedList;

public class ServerMsg implements Serializable {
    static final long serialVersionUID=1;
    private String msg;
    private LinkedList<CarView> cars;

    public ServerMsg(LinkedList<CarView> cars,String msg){
        this.cars=cars;
        this.msg=msg;
    }

    public String getMsg() {
        return msg;
    }

    public LinkedList<CarView> getCars() {
        return cars;
    }

    @Override
    public String toString() {
        return "ServerMsg{" +
                "msg='" + msg + '\'' +
                ", cars=" + cars +
                '}';
    }
}
