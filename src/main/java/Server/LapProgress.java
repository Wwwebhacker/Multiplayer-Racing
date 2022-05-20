package Server;

import Shared.CheckPoint;

import java.util.ArrayList;

public class LapProgress {
    private static ArrayList<CheckPoint> checkPoints=new ArrayList<>();
    private static int numberOfCheckpoints;

    public static void addCheckPoint(CheckPoint checkPoint){
        checkPoints.add(checkPoint);
        numberOfCheckpoints++;
    }


}
