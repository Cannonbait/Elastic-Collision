import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class Model implements IBouncingBallsModel {

	private final double areaWidth;
	private final double areaHeight;
	private final List<Ball> balls = new ArrayList<>();
  private final double GRAVITY = 9.82;

	public Model(double width, double height) {
		this.areaWidth = width;
		this.areaHeight = height;
    
    for (int x = 3; x < 24; x += 3){
      balls.add(new Ball(x, 3));
      balls.add(new Ball(x + 0.1, 6));
      balls.add(new Ball(x + 0.2, 9));
      balls.add(new Ball(x + 0.3, 12));
      balls.add(new Ball(x + 0.4, 15));
      balls.add(new Ball(x + 0.5, 18));
      balls.add(new Ball(x + 0.6, 21));
      balls.add(new Ball(x + 0.7, 24));
    }
  }

	@Override
	public void tick(double deltaT) {

    handleCollisions(deltaT);

    applyGravity(deltaT);

    moveBalls(deltaT);
	}

  private void clearCollisions(){
    for (Ball a : balls){
      for (Ball b : balls){
        if (a != b && !a.collidesWith(b)){
          a.removeCollision(b);
          b.removeCollision(a);
        }
      }
    }
  }

  private void handleCollisions(double deltaT){
    ballCollisions(deltaT);
    wallCollisions(deltaT);

  }

  private void ballCollisions(double deltaT){
    for (Ball a : balls){
      for (Ball b : balls){
        if (a != b && a.collidesWith(b)){
          ballCollision(a, b);
        }
      }
    }  
  }


  private void ballCollision(Ball a, Ball b){
    Vector collisionVector = calculateCollisionVector(a, b);

    Vector aV = a.getMovementVector();
    Vector aP = aV.projection(collisionVector);
    double aPL = aV.projectionLength(collisionVector);
    Vector aR = aV.rejection(collisionVector);

    Vector bV = b.getMovementVector();
    Vector bP = bV.projection(collisionVector);
    double bPL = bV.projectionLength(collisionVector);
    Vector bR = bV.rejection(collisionVector);

    //Based on aP and bP we need to calculate the new vectors on the collision axis
    //final double momentum 
    
    if (activeCollision(a, b)){
      setMovementVectors(bP, aR, a);
      setMovementVectors(aP, bR, b);
    }
  }

  private boolean activeCollision(Ball a, Ball b){
    Vector collisionVector = calculateCollisionVector(a, b);
    double aLength = a.getMovementVector().projectionLength(collisionVector);
    double bLength = b.getMovementVector().projectionLength(collisionVector);

    if ((aLength > 0 && bLength < 0) || bLength < aLength){
      return false;
    }
    return true;
  }

  private void setMovementVectors(Vector vP, Vector vR, Ball ball){
    Vector xNorm = new Vector(1, 0), yNorm = new Vector(0, 1);
    Vector vectorVx = vP.projection(xNorm).add(vR.projection(xNorm));
    Vector vectorVy = vP.projection(yNorm).add(vR.projection(yNorm));
    ball.setVx(vectorVx.getX());
    ball.setVy(vectorVy.getY());
  }

  private Vector calculateCollisionVector(Ball a, Ball b){
    double x = a.getX() - b.getX();
    double y = a.getY() - b.getY();
    return new Vector(x, y).normalize();
  }

  private void wallCollisions(double deltaT){
    for (Ball ball : balls){
      double x = ball.getX(), y = ball.getY(), r = ball.getR();
      if ((x < r && ball.getVx() <= 0) || (x > areaWidth - r && ball.getVx() >= 0)) {
        ball.setVx(ball.getVx() * -1);

      }
      if ((y < r && ball.getVy() <= 0) || (y > areaHeight - r && ball.getVy() >= 0)) {
        ball.setVy(ball.getVy() * -1);
        ball.setGravity(false);
      } else {
        ball.setGravity(true);
      }
    }
  }

  private void applyGravity(double deltaT){
    for (Ball ball : balls){
      ball.applyGravity(deltaT * GRAVITY);
    }
  }

  private void moveBalls(double deltaT){
    for (Ball ball : balls){
      ball.move(deltaT);
    }
  }
    
  @Override
	public List<Ellipse2D> getBalls() {
		List<Ellipse2D> myBalls = new LinkedList<Ellipse2D>();
    for (Ball ball : balls){
      double x = ball.getX(), y = ball.getY(), r = ball.getR();
		  myBalls.add(new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r));
      
    }
		return myBalls;
	}
}
