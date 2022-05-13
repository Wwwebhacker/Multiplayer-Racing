package Client;

import javax.swing.*;

public class Frame extends JFrame {


    public Frame(){
        this.add(new Panel());
        this.pack();
        this.setTitle("Racing");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.setVisible(true);



    }


}