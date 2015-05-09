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
		balls.add(new Ball(1, 5));
	}

	@Override
	public void tick(double deltaT) {

    handleCollisions(deltaT);

    applyGravity(deltaT);

    moveBalls(deltaT);
		
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
  }

  private Vector calculateCollisionVector(Ball a, Ball b){
    double x = a.getX() - b.getX();
    double y = a.getY() - b.getY();
    return (new Vector(x, y)).normalize();
  }

  private void wallCollisions(double deltaT){
    for (Ball ball : balls){
      double x = ball.getX(), y = ball.getY(), r = ball.getR();
      if (x < r || x > areaWidth - r) {
        ball.setVx(ball.getVx() * -1);

      }
      if (y < r || y > areaHeight - r) {
        ball.setVy(ball.getVy() * -1);
        ball.setNoGravity();
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
