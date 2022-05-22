package Server;

import Shared.CheckPoint;

public class RaceProgress {

    private static int numberOfCheckpoints=0;
    private int lap=0;
    private int progress=0;
    private int prevCheckpoint=0;

    private int raceResults=0;

    public static void setNumberOfCheckpoints(int numberOfCheckpoints) {
        RaceProgress.numberOfCheckpoints = numberOfCheckpoints;
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

    public int getPrevCheckpoint() {
        return prevCheckpoint;
    }

    public String getProgressMsg(){
        return "lap: "+lap+" checkpoint: "+progress;
    }

    public boolean checkIfEnd(){
        if (lap>=5){
            return true;
        }
        return false;
    }

    public void setRaceResults(int raceResults) {
        this.raceResults = raceResults;
    }

    public int getRaceResults() {
        return raceResults;
    }
}
