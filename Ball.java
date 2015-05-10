
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Cannonbait
 */
public class Ball {
  private double x, y, vx, vy, r;
  private boolean gravity = true;
  private List<Ball> collisions = new ArrayList<>();

  
  public Ball(double x, double y) {
    //r = Math.random()*0.9+0.1;
    r = 1.0;
    this.x = x;
    this.y = y;
    vx = 1;
    vy = 0;
  }
  
  public boolean collidesWith(Ball ball){
    double xDiff = getX()-ball.getX();
    double yDiff = getY()-ball.getY();
    double distance = Math.sqrt(xDiff*xDiff + yDiff*yDiff);
    return (distance < getR() + ball.getR());
  }

  public void addCollision(Ball ball){
    collisions.add(ball);
  }

  public boolean hasNotCollidedWith(Ball ball){
    return !collisions.contains(ball);
  }

  public void removeCollision(Ball b){
    if (collisions.contains(b)){
      collisions.remove(b);
    }
  }

  
  public void move(double deltaT){
    x += vx * deltaT;
    y += vy * deltaT;
  }
  
  public void move(Vector dir){
    x += dir.getX();
    y += dir.getY();
  }

  public void applyGravity(double constant){
    if (gravity){
      vy -= constant;
    }
  }

  public void setGravity(boolean value){
    gravity = value;
  }

  public double getMomentum(){
    return getMass() * vx + getMass() * vy;
  }
  
  public double getMass(){
    return 1;
    //return r*r*Math.PI;
  }

  public String toString(){
    return x + ", " + y + "\t" + vx + ", " + vy;
  }
  
  public Vector getMovementVector(){
    return new Vector(vx, vy);
  }
  
  public double getX(){
    return x;
  }

  public double getY(){
    return y;
  }
  
  public double getR(){
    return r;
  }
  
  public double getVx(){
    return vx;
  }
  
  public double getVy(){
    return vy;
  }
  
  public void setX(double x){
    this.x = x;
  }
  
  public void setY(double y){
    this.y = y;
  }
  
  public void setVx(double vx){
    this.vx = vx;
  }
  
  public void setVy(double vy){
    this.vy = vy;
  }
  

}
