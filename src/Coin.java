import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Random;

public class Coin extends Item{
	//COSTANTI
	private static int HITBOX_X=50;
	private static int HITBOX_Y=50;
	private static String IMAGE_NAME="gameImages/coin/coin.png";
	private static int POINTS=50;
	private static int SPEED=0;
	private static int DAMAGE=1;
	private static int TEMPO_MAX=3;
	//ATTRIBUTI
	private int tempoMax;
	//COSTRUTTORI
	public Coin(int x,int y) {
		super(x,y,HITBOX_X,HITBOX_Y,IMAGE_NAME,POINTS,SPEED,DAMAGE);
		tempoMax=TEMPO_MAX;
	}
	//METODI
	public int getTm() {return tempoMax;}
	public void decreaseTm() {
			tempoMax--;
	}
}
