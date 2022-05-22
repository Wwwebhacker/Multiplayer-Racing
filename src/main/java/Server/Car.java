package Server;

import Shared.CheckPoint;
import Shared.Obstacle;
import Shared.Vector;

import java.awt.*;


public class Car {

    private RaceProgress raceProgress;

    private Vector pos;
    private double vel;
    private Color color;
    private double maxspeed=20;
    private Vector heading;
    private double alpha=0;

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public RaceProgress getRaceProgress() {
        return raceProgress;
    }

    public Color getColor() {
        return color;
    }

    public double getAlpha() {
        return alpha;
    }

    public Vector getHeading() {
        return new Vector(heading);
    }

    public Vector getPos() {
        return new Vector(pos);
    }

    public void setPos(Vector pos) {
        this.pos = pos;
    }

    public double getVel() {
        return vel;
    }

    public void setVel(double vel) {
        this.vel = vel;
    }

    public Car(double maxspeed, double x, double y,Color color){
        this.raceProgress=new RaceProgress();
        this.maxspeed=maxspeed;
        this.color=color;

        heading=new Vector(1,1);
        pos=new Vector(x,y);
        vel=0;


        calcHeading();
    }

    public boolean collision(Obstacle obstacle){
        double x1=obstacle.getX1();
        double y1=obstacle.getY1();
        double x2=obstacle.getX2();
        double y2=obstacle.getY2();

        Vector line=new Vector(x2-x1,y2-y1);
        double len=line.mag();
        Vector startOfLine_car=new Vector(pos.x-x1,pos.y-y1);


        double dot= Vector.dotProduct(line,startOfLine_car);
        double t=dot/(len*len);
        t=Math.max(0,t);
        t=Math.min(1,t);





        Vector closestPoint=line;
        closestPoint.mul(t);
        closestPoint.x+=x1;
        closestPoint.y+=y1;
        Vector tmp=closestPoint.copy();
        tmp.sub(pos);
        double dist=tmp.mag();



        if (dist<10){
            if (obstacle.getClass()!= CheckPoint.class){
                Vector newp=new Vector(closestPoint);
                newp.sub(pos);
                newp.normalize();
                newp.mul(-10);
                newp.add(closestPoint);
                pos=newp;
                vel*=0.9;
            }else {
                CheckPoint f=(CheckPoint) obstacle;
                raceProgress.advance(f);
            }

            return true;
        }
        return false;
    }
    public void calcPos(){

        Vector v=heading.copy();

        v.mul(vel);
        pos.add(v);
    }
    public void gas(){
        vel+=0.07;
        vel=Math.min(maxspeed,vel);
    }
    public void brake(){
        vel-=0.1;
        vel=Math.max(-2,vel);
    }
    public void friction(){
        double p=0.003*vel;
        if (vel>=p){
            vel-=p;
        }else
        if (vel>=-p){
            vel+=p;
        }else {
            vel=vel*0.98;
        }

    }
    private void calcHeading(){

        heading.x=Math.cos(alpha*Math.PI/180);
        heading.y=Math.sin(alpha*Math.PI/180);

    }
    private void steer(int dir){// dir: -1 left, 1 right

        if (vel<0){
            dir*=-1;
        }
        double alphaModif;
        double veltmp=Math.abs(vel);
        if (veltmp<2){
            alphaModif=1.3*Math.abs(veltmp);
        }else {
            alphaModif=-veltmp/5+3;
            if (alphaModif<0){
                alphaModif=0;
            }
        }

        if (dir==-1){
            //alpha-=(10)/Math.max(2,0.7*vel);
            alpha-=alphaModif;
            if (alpha<0){
                alpha=360-alpha;
            }
        }
        if (dir==1){
            //alpha+=(10)/Math.max(2,0.7*vel);
            alpha+=alphaModif;
            if (alpha>360){
                alpha=alpha-360;
            }
        }
        calcHeading();
    }
    public void left(){
        steer(-1);
    }
    public void right(){
        steer(1);
    }


}
