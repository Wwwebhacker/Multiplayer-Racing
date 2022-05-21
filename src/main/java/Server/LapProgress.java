package Server;

import Shared.CheckPoint;

import java.util.ArrayList;

public class LapProgress {

    private static int numberOfCheckpoints=0;
    private int lap=0;
    private int progress=0;
    private int prevCheckpoint=0;


    public static void setNumberOfCheckpoints(int numberOfCheckpoints) {
        LapProgress.numberOfCheckpoints = numberOfCheckpoints;
    }

    public void advance(CheckPoint checkPoint){
        int orderNumber= checkPoint.getOrderNumber();
        if (orderNumber>prevCheckpoint){
            progress++;
            prevCheckpoint=orderNumber;
        }else
        if (progress==(numberOfCheckpoints-1)){
            if (orderNumber==0){
                lap++;
                progress=0;
                prevCheckpoint=orderNumber;
            }
        }
    }
    public String getProgressMsg(){
        return "lap: "+lap+"; checkpoint: "+progress;
    }


}
