

public class Ball {
  private double x, y, vx, vy, r;
  private boolean gravity = true;
  private final RgbColor color;

  
  public Ball(double x, double y) {
    //r = Math.random()*0.9+0.1;
    this(x, y, Math.random(), Math.random(), Math.random()*0.9 + 0.1);
  }

  public Ball(double x, double y, double vx, double vy, double r){
    this.x = x;
    this.y = y;
    this.vx = vx;
    this.vy = vy;
    this.r = r;
    color = new RgbColor();
  }
  
  public boolean collidesWith(Ball ball){
    double xDiff = getX()-ball.getX();
    double yDiff = getY()-ball.getY();
    double distance = Math.sqrt(xDiff*xDiff + yDiff*yDiff);
    return (distance < getR() + ball.getR());
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

  public RgbColor getColor(){
    return color;
  }

  public double getMomentum(){
    return getMass() * vx + getMass() * vy;
  }
  
  public double getMass(){
    return r*r*Math.PI;
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
