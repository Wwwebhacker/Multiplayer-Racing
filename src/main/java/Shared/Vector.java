package Shared;

import java.io.Serializable;

public class Vector implements Serializable {
    static final long serialVersionUID=1;
    public double x,y;
    public Vector(double x,double y){
        this.x=x;
        this.y=y;
    }
    public Vector(Vector other){
        this.x=other.x;
        this.y=other.y;
    }
    public static Vector add(Vector v1,Vector v2){
        return new Vector(v1.x+ v2.x, v1.y+ v2.y);
    }
    public static Vector sub(Vector v1,Vector v2){
        return new Vector(v1.x- v2.x, v1.y- v2.y);
    }
    public Vector set(double x,double y){
        this.x=x;
        this.y=y;
        return this;
    }
    public Vector limit(double m){

        if (this.mag()>m){
            this.setMag(m);
        }
        return this;
    }
    public Vector copy(){
        return new Vector(this);
    }
    public Vector setMag(double mag){
        this.normalize();
        this.mul(mag);
        return this;
    }
    public static double dotProduct(Vector v1,Vector v2){
        double product=0;
        product+=v1.x*v2.x;
        product+=v1.y*v2.y;
        return product;
    }
    public Vector normalize(){
        double len=Math.sqrt(x*x+y*y);
        this.div(len);

        return this;
    }
    public double mag(){
        return Math.sqrt(x*x+y*y);
    }
    public Vector set(Vector v){
        this.x=v.x;
        this.y=v.y;
        return this;
    }
    public Vector add(Vector v2){
        x+=v2.x;
        y+=v2.y;
        return this;
    }
    public Vector sub(Vector v2){
        x-=v2.x;
        y-=v2.y;
        return this;
    }
    public Vector mul(double m){
        x*=m;
        y*=m;
        return this;
    }
    public Vector div(double m){
        if (m!=0){
            x/=m;
            y/=m;
        }
        return this;

    }

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
