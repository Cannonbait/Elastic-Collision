import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.List;

/**
 * Extends Animator with capability to draw a bouncing balls model.
 * 
 * This class can be left unmodified for the bouncing balls lab. :)
 * 
 * @author Oscar Soderlund
 * 
 */
@SuppressWarnings("serial")
public final class BouncingBalls extends Animator {

	private static final double PIXELS_PER_METER = 30;

	private IBouncingBallsModel model;
	private double modelHeight;
	private double deltaT;

	@Override
	public void init() {
		super.init();
		double modelWidth = canvasWidth / PIXELS_PER_METER;
		modelHeight = canvasHeight / PIXELS_PER_METER;
		model = new Model(modelWidth, modelHeight);                
	}

	@Override
	protected void drawFrame(Graphics2D g) {
		// Clear the canvas
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, canvasWidth, canvasHeight);
		// Update the model
		model.tick(deltaT);
		List<Ball> balls = model.getBalls();
		// Transform balls to fit canvas
		
		g.scale(PIXELS_PER_METER, -PIXELS_PER_METER);
		g.translate(0, -modelHeight);
		for (Ball b : balls) {
      double x = b.getX(), y = b.getY(), r = b.getR();
      RgbColor color = b.getColor();
			g.setColor(toAwtColor(color));
			g.fill(new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r));
		}
	}

	private Color toAwtColor(RgbColor rgbColor){
		return new Color((float)rgbColor.getR(), (float)rgbColor.getG(), (float)rgbColor.getB());
	}

	@Override
	protected void setFrameRate(double fps) {
		super.setFrameRate(fps);
		// Update deltaT according to new frame rate
		deltaT = 1 / fps;
	}
}
