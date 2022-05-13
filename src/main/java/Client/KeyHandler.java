package Client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean up,down,left,right;
    public boolean up2,down2,left2,right2;
    public boolean editor;
    @Override
    public void keyPressed(KeyEvent e) {
        int code=e.getKeyCode();

        if (code==KeyEvent.VK_E){
            editor=true;
        }

        if (code==KeyEvent.VK_W){
            up=true;
        }
        if (code==KeyEvent.VK_S){
            down=true;
        }
        if (code==KeyEvent.VK_A){
            left=true;
        }
        if (code==KeyEvent.VK_D){
            right=true;
        }

        if (code==KeyEvent.VK_UP){
            up2=true;
        }
        if (code==KeyEvent.VK_DOWN){
            down2=true;
        }
        if (code==KeyEvent.VK_LEFT){
            left2=true;
        }
        if (code==KeyEvent.VK_RIGHT){
            right2=true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code=e.getKeyCode();

        if (code==KeyEvent.VK_E){
            editor=false;
        }

        if (code==KeyEvent.VK_W){
            up=false;
        }
        if (code==KeyEvent.VK_S){
            down=false;
        }
        if (code==KeyEvent.VK_A){
            left=false;
        }
        if (code==KeyEvent.VK_D){
            right=false;
        }

        if (code==KeyEvent.VK_UP){
            up2=false;
        }
        if (code==KeyEvent.VK_DOWN){
            down2=false;
        }
        if (code==KeyEvent.VK_LEFT){
            left2=false;
        }
        if (code==KeyEvent.VK_RIGHT){
            right2=false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
