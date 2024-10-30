import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Camera extends Item{
	//COSTANTI
	private static String IMAGE_NAME="gameImages/gamePanels/camera.png";
	//ATTRIBUTI
	//COSTRUTTORI
	public Camera(int x,int y) {
		super(x,y,IMAGE_NAME);
		//immagine= new BufferedImage(Board.NUMERO_COLONNE*Board.DIM_X_CELLA,Board.NUMERO_RIGHE*Board.DIM_Y_CELLA,BufferedImage.TYPE_INT_RGB);
	}
	//METODI
}
