package Server;

import Shared.FinishLine;
import Shared.Obstacle;
import Shared.Vector;



public class Car {
    private Vector pos;
    private double vel;

    private double maxspeed=20;
    private Vector heading;
    private double alpha=0;

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

    public Car(double maxspeed, double x, double y){
        this.maxspeed=maxspeed;


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
            if (obstacle.getClass()!= FinishLine.class){
                Vector newp=new Vector(closestPoint);
                newp.sub(pos);
                newp.normalize();
                newp.mul(-10);
                newp.add(closestPoint);
                pos=newp;
                vel*=0.95;
            }else {
                FinishLine f=(FinishLine) obstacle;
                f.crossLine();
                f.printTime();
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
        vel+=0.1;
        vel=Math.min(maxspeed,vel);
    }
    public void brake(){
        vel-=0.1;
        vel=Math.max(-2,vel);
    }
    public void friction(){
        vel=vel*0.99;
    }
    private void calcHeading(){

        heading.x=Math.cos(alpha*Math.PI/180);
        heading.y=Math.sin(alpha*Math.PI/180);

    }
    private void steer(int dir){// dir: -1 left, 1 right
        if (vel<0){
            dir*=-1;
        }
        if (dir==-1){
            alpha-=(10)/Math.max(2,0.7*vel);
            if (alpha<0){
                alpha=360;
            }


        }
        if (dir==1){
            alpha+=(10)/Math.max(2,0.7*vel);
            if (alpha>360){
                alpha=0;
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
