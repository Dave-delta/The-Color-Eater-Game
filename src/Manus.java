import java.util.Random;

public class Manus extends Item {
	//ATTRIBUTI
	private static int HITBOX_X=50;
	private static int HITBOX_Y=50;
	private static String IMAGE_NAME="gameImages/manus/manus.png";
	private static int POINTS=0;
	private static int SPEED=0;
	private static int DAMAGE=0;
	
	int id;
	//COSTRUTTORI
	public Manus(int x,int y){
		super(x,y,HITBOX_X,HITBOX_Y,IMAGE_NAME,POINTS,SPEED,DAMAGE);
	}
	//METODI
	public void setId(int id) {this.id=id;}
	public int getId() {return id;}
	public int generateId() {
		Random rand=new Random();
		setId(1+rand.nextInt(3));
		return id;
	}
}
