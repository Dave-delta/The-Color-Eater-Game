import java.awt.Point;
import java.awt.Rectangle;

public class Enemy extends Item{
	//ATTRIBUTI
	private static int HITBOX_X=50;
	private static int HITBOX_Y=50;
	private static String IMAGE_NAME[]= {"gameImages/enemy/enemy_0.png","gameImages/enemy/enemy_1.png","gameImages/enemy/enemy_2.png"};
	private static int POINTS=50;
	private static int SPEED=4;
	private static int DAMAGE=1;
	
	private static int lArea=5;
	private Point startPosition;
	private int dX;
	private int dY;
	private int direzione;
	private Rectangle range;
	private Manus manus;
	//COSTRUTTORI
	public Enemy(int x,int y) {
		super(x,y,HITBOX_X,HITBOX_Y,IMAGE_NAME,POINTS,SPEED,DAMAGE);
		startPosition=new Point(x,y);
		dX=Board.DIM_X_CELLA*((lArea-1)/2);
		dY=Board.DIM_Y_CELLA*((lArea-1)/2);
		range=new Rectangle(posizione.x-dX,posizione.y-dY,lArea*Board.DIM_X_CELLA,lArea*Board.DIM_Y_CELLA);
		manus= new Manus(startPosition.x,startPosition.y);
	}
	//METODI
	public void setStartPosition(Point posizione) {
		startPosition=posizione;
	}
	public Point getStartPosition() {return startPosition;}
	public void enemyMoveTo(Point p){
		if(posizione.x<p.x ) {
			if(p.x-posizione.x<velocita) {
				posizione.translate(2,0);
			}
			else {
				posizione.translate(velocita,0);
			}
		}
		else if(posizione.x>p.x) {
			if(posizione.x-p.x<velocita) {
				posizione.translate(-2,0);
			}
			else {
				posizione.translate(-velocita,0);
			}
		}
		if(posizione.y<p.y) {
			if(p.y-posizione.y<velocita) {
				posizione.translate(0,2);
			}
			else {
				posizione.translate(0,velocita);
			}
		}
		else if(posizione.y>p.y) {
			if(posizione.y-p.y<velocita) {
				posizione.translate(0,-2);
			}
			else {
				posizione.translate(0,-velocita);
			}
		}
		calculatePHitBox();
		calculatePRange();
	}
	public Rectangle getRange() {return range;}
	private void calculatePRange() {
		range.x=posizione.x-dX;
		range.y=posizione.y-dY;
	}
	public Manus getManus() {return manus;}
	public void setManusNULL() {
		manus=null;
	}
	public boolean IsManusActive() {
		if(manus!=null) {
			return manus.getId()!=0;
		}
		return false;
	}
	public void tickForManus() {
		if(manus!=null) {
			if((posizione.x==startPosition.x&&posizione.y==startPosition.y)) {
				manus.setId(0);
			}
			else{
				if(manus.getId()==0) {
					manus.generateId();
				}
			}
		}	
	}
	public void activeFury() {
		if(velocita==SPEED)
		velocita*=2;
	}
	public int getNDraw() {
		if(manus==null) {
			return 2;
		}
		if(posizione.x==startPosition.x&&posizione.y==startPosition.y) {
			return 0;
		}
		else {
			return 1;
		}
	}
}
