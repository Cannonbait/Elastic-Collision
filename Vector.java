/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cannonbait
 */
public class Vector {
    private final double x, y;
    
    public Vector (double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public Vector (Vector v, double length){
        Vector normalized = v.normalize();
        x = normalized.getX() * length;
        y = normalized.getY() * length;
    }
    
    public double dotProduct(Vector b){
        return x*b.getX()+y*b.getY();
    }
    
    public double getDistance(Vector b){
        return Math.hypot(x-b.getX(), y-b.getY());
    }
    
    public double getLength(){
        return Math.hypot(x, y);
    }
    
    public Vector normalize(){
        double len = Math.hypot(x, y);
        return new Vector(x/len, y/len);
    }
    
    public Vector projection(Vector b){
        double length = this.dotProduct(b)/b.dotProduct(b);
        return new Vector(length*b.getX(), length*b.getY());
    }
    
    public Vector rejection(Vector b){
        return projection(new Vector(b.getY()*-1, b.getX()));
    }
    
    public Vector add(Vector b){
        return new Vector(x + b.getX(), y + b.getY());
    }
    
    public Vector getReverse(){
        return new Vector(y*-1, x);
    }
    
    public String toString(){
        return "X: " + x + "\tY: " + y;
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
}