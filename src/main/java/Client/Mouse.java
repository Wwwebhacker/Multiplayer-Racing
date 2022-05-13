package Client;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseMotionListener,MouseListener {
    int x;
    int y;
    boolean pressed=false;
    @Override
    public void mouseDragged(MouseEvent e) {
        //System.out.println("screen(X,Y) = " + e.getX() + "," + e.getY());
       // mpos.x=e.getX();
       // mpos.y=e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //System.out.println("screen(X,Y) = " + e.getX() + "," + e.getY());

        x=e.getX();
        y=e.getY();
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed=true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressed=false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
