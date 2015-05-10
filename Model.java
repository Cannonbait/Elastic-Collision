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
    balls.add(new Ball(1, 2));
	}

	@Override
	public void tick(double deltaT) {

    handleCollisions(deltaT);

    applyGravity(deltaT);

    moveBalls(deltaT);

    clearCollisions();
		
	}

  private void clearCollisions(){
    for (Ball ball : balls){
      ball.emptyCollisions();
    }
  }

  private void handleCollisions(double deltaT){
    ballCollisions(deltaT);
    wallCollisions(deltaT);

  }

  private void ballCollisions(double deltaT){
    for (Ball a : balls){
      for (Ball b : balls){
        if (a != b && a.collidesWith(b) && a.hasNotCollidedWith(b)){
          a.addCollision(b);
          b.addCollision(a);
          ballCollision(a, b);
        }
      }
    }  
  }

  private void ballCollision(Ball a, Ball b){
    Vector xNorm = new Vector(1, 0), yNorm = new Vector(0, 1);
    Vector collisionVector = calculateCollisionVector(a, b);

    Vector aV = a.getMovementVector();
    Vector aP = aV.projection(collisionVector);
    Vector aR = aV.rejection(collisionVector);

    Vector bV = b.getMovementVector();
    Vector bP = bV.projection(collisionVector);
    Vector bR = bV.rejection(collisionVector);

    //Based on aP and bP we need to calculate the new vectors on the collision axis
    //final double momentum 
    
    Vector newAp = bP;
    Vector newAVectorVx = newAp.projection(xNorm).add(aR.projection(xNorm));
    Vector newAVectorVy = newAp.projection(yNorm).add(aR.projection(yNorm));
    System.out.println("From: " + a.getVy() + "\t To: " + newAVectorVy.getY());
    a.setVx(newAVectorVx.getX());
    a.setVy(newAVectorVy.getY());
    

    Vector newBp = aP;
    Vector newBVectorVx = newBp.projection(xNorm).add(bR.projection(xNorm));
    Vector newBVectorVy = newBp.projection(yNorm).add(bR.projection(yNorm));
    System.out.println("From: " + b.getVy() + "\t To: " + newBVectorVy.getY());
    b.setVx(newBVectorVx.getX());
    b.setVy(newBVectorVy.getY());
  }

  private Vector calculateCollisionVector(Ball a, Ball b){
    double x = a.getX() - b.getX();
    double y = a.getY() - b.getY();
    return new Vector(x, y).normalize();
  }

  private void wallCollisions(double deltaT){
    for (Ball ball : balls){
      double x = ball.getX(), y = ball.getY(), r = ball.getR();
      if (x < r || x > areaWidth - r) {
        ball.setVx(ball.getVx() * -1);

      }
      if (y < r || y > areaHeight - r) {
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
