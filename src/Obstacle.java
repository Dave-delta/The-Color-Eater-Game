import java.awt.Point;

public class Obstacle extends Item{
	private static int HITBOX_X=50;
	private static int HITBOX_Y=50;
	private static String IMAGE_NAME="gameImages/obstacle/obstacle.png";
	private static int POINTS=200;
	private static int SPEED=0;
	private static int DAMAGE=2;
	public Obstacle(int x,int y) {
		super(x,y,HITBOX_X,HITBOX_Y,IMAGE_NAME,POINTS,SPEED,DAMAGE);
	}
}
