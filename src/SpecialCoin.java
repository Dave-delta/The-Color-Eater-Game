import java.awt.Point;
import java.util.Random;

public class SpecialCoin extends Item {
	//COSTANTI
	private static int HITBOX_X=50;
	private static int HITBOX_Y=50;
	private static String IMAGE_NAME="gameImages/coin/specialCoin.png";
	private static int POINTS=200;
	private static int SPEED=6;
	private static int DAMAGE=2;
	//ATTRIBUTI
	private int direzione;
	//COSTRUTTORI
	public SpecialCoin(int x, int y) {
		super(x,y,HITBOX_X,HITBOX_Y,IMAGE_NAME,POINTS,SPEED,DAMAGE);
		direzioneRand();
	}
	//METODI
	public void setDirezione(int direzione) {
		this.direzione=direzione;
	}
	public void direzioneRand() {
		Random rand= new Random();
		boolean errore=true;
		int random=0;
		while(errore) {
			errore=false;
			random=rand.nextInt(8);
			if(random==direzione) {
				errore=true;
			}
		}
		direzione=random;
	}
	public int getDirezione() {return direzione;}
	public void SpecialCoinMove(){
		switch(direzione) {
			case 0:
				posizione.translate(0,-velocita);
			break;
			case 1:
				posizione.translate(0,velocita);
			break;
			case 2:
				posizione.translate(velocita,0);
			break;
			case 3:
				posizione.translate(-velocita,0);
			break;
			case 4:
				posizione.translate(velocita,-velocita);
			break;
			case 5:
				posizione.translate(velocita,velocita);
			break;
			case 6:
				posizione.translate(-velocita,velocita);
			break;
			case 7:
				posizione.translate(-velocita,-velocita);
			break;
		}
	}
	public void tick() {
		if(posizione.getX()<Board.DIM_X_CELLA*Board.LIM_X) {
			posizione.setLocation(Board.DIM_X_CELLA*Board.LIM_X, posizione.getY());
			direzioneRand();
		}
		if(posizione.getX()>(Board.NUMERO_COLONNE-Board.LIM_X)*Board.DIM_X_CELLA) {
			posizione.setLocation((Board.NUMERO_COLONNE-Board.LIM_X)*Board.DIM_X_CELLA,posizione.getY());
			direzioneRand();
		}
		if(posizione.getY()<Board.DIM_Y_CELLA*Board.LIM_Y) {
			posizione.setLocation( posizione.getX(),Board.DIM_Y_CELLA*Board.LIM_Y);
			direzioneRand();
		}
		if(posizione.getY()>(Board.NUMERO_RIGHE-Board.LIM_Y)*Board.DIM_Y_CELLA) {
			posizione.setLocation(posizione.getX(),(Board.NUMERO_RIGHE-Board.LIM_Y)*Board.DIM_Y_CELLA);
			direzioneRand();
		}
		calculatePHitBox();
	}
}
