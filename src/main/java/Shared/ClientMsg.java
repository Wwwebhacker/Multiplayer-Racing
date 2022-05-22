package Shared;

import java.io.Serializable;

public class ClientMsg implements Serializable {
    private boolean up=false;
    private boolean down=false;
    private boolean left=false;
    private boolean right=false;
    static final long serialVersionUID=1;
    public void setDown(boolean down) {
        this.down = down;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isUp() {
        return up;
    }

    @Override
    public String toString() {
        return "ClientMsg{" +
                "up=" + up +
                ", down=" + down +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
