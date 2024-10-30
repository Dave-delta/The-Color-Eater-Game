import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import javax.imageio.ImageIO;

public class Item {
	//COSTANTI
	private static int MAX_PUNTEGGIO=5000;
	//ATTRIBUTI
	protected BufferedImage immagine;
	protected BufferedImage	imageVector[]=new BufferedImage[10];
	protected Point posizione;
	protected int punteggio;
	protected int velocita;
	protected Rectangle hitBox;
	protected int danno;
	//COSTRUTTORI	
	public Item(int x,int y,int dX,int dY,String nome,int punteggio,int velocita,int danno) {
		posizione=new Point(x,y);
		caricaImmagine(nome);
		this.punteggio=punteggio;
		this.velocita=velocita;
		hitBox= new Rectangle(posizione.x+(Board.DIM_X_CELLA/2-dX/2),posizione.y+(Board.DIM_Y_CELLA/2-dY/2),dX,dY);
		this.danno=danno;
	}
	public Item(int x,int y,int dX,int dY,String nome[],int punteggio,int velocita,int danno) {
		posizione=new Point(x,y);
		caricaImmagineTypeArray(nome);
		this.punteggio=punteggio;
		this.velocita=velocita;
		hitBox= new Rectangle(posizione.x+(Board.DIM_X_CELLA/2-dX/2),posizione.y+(Board.DIM_Y_CELLA/2-dY/2),dX,dY);
		this.danno=danno;
	}
	public Item(int x,int y,String nome) {
		posizione=new Point(x,y);
		caricaImmagine(nome);
	}
	public Item(int x,int y,String nome[]) {
		posizione=new Point(x,y);
		caricaImmagineTypeArray(nome);
	}
	//METODI
	public void draw(Graphics g,ImageObserver osservatore) {
		//metodo che permette di disegnare l'immagiine su un oggetto in cui può essere disegnato qualcosa
		g.drawImage(immagine,posizione.x,posizione.y,osservatore);
	}
	public void drawTypeArray(Graphics g,ImageObserver osservatore,int i) {
		//metodo che permette di disegnare l'immagiine su un oggetto in cui può essere disegnato qualcosa
		g.drawImage(imageVector[i],posizione.x,posizione.y,osservatore);
	}
	private void caricaImmagine(String nome) {
		//metodo per caricare l'immagine
		try {
			immagine=ImageIO.read( new File(nome));
		}
		catch(Exception e) {
			System.out.println("ERRORE apertura del file"+e.getMessage());
		}
	}
	private void caricaImmagineTypeArray(String nome[]) {
		//metodo per caricare l'immagine
		for(int i=0;i<nome.length;i++) {
			try {
				imageVector[i]=ImageIO.read( new File(nome[i]));
			}
			catch(Exception e) {
				System.out.println("ERRORE apertura del file"+e.getMessage());
			}
		}	
	}
	public Point getPosizione() {
		return posizione;
	}
	public void setPosizione(int x,int y) {
		posizione.x=x;
		posizione.y=y;
	}
	public void incrementPosX(int incremento) {
		posizione.x+=incremento;
	}
	public void incrementPosY(int incremento) {
		posizione.y+=incremento;
	}
	public int getDistance(Point p) {
		return (int) Math.sqrt((Math.pow((posizione.x-p.getX()), 2))+(Math.pow((posizione.x-p.getX()), 2)));
	}
	
	public int getPunteggio() {
		return punteggio;
	}
	public void setPunteggio(int punteggio) {
		this.punteggio=punteggio;
		if(this.punteggio>MAX_PUNTEGGIO) {
			this.punteggio=MAX_PUNTEGGIO;
		}
		else if(this.punteggio<0) {
			this.punteggio=0;
		}	
	}
	public int getMAX_PUNTEGGIO() {return MAX_PUNTEGGIO;}
	public void setVelocita(int velocita) {
		this.velocita=velocita;
	}
	public int getVelocita() {return velocita;}
	public int getDanno() {return danno;}
	public BufferedImage getImage() {return immagine;}
	public Rectangle getHitBox() {return hitBox;}
	public boolean IsInside(Rectangle r) {
		Point PSx=new Point(hitBox.x,hitBox.y);
		Point PDx=new Point(hitBox.x+hitBox.width,hitBox.y);
		Point pDx=new Point(hitBox.x+hitBox.width,hitBox.y+hitBox.height);
		Point pSx=new Point(hitBox.x,hitBox.y+hitBox.height);
		return IsPointIn(PSx,r) || IsPointIn(PDx,r) || IsPointIn(pDx,r) || IsPointIn(pSx,r);
	}
	public boolean IsPointIn(Point posizione, Rectangle r) {
		return posizione.x>=r.x && posizione.x<=r.x+r.width && posizione.y>=r.y && posizione.y<=r.y+r.height;
	}
	protected void calculatePHitBox() {
		hitBox.x=posizione.x+(Board.DIM_X_CELLA/2-hitBox.width/2);
		hitBox.y=posizione.y+(Board.DIM_Y_CELLA/2-hitBox.height/2);
	}
	public void tick() {
		if(posizione.getX()<Board.DIM_X_CELLA*Board.LIM_X) {
			posizione.setLocation(Board.DIM_X_CELLA*Board.LIM_X, posizione.getY());
		}
		if(posizione.getX()>(Board.NUMERO_COLONNE-Board.LIM_X)*Board.DIM_X_CELLA) {
			posizione.setLocation((Board.NUMERO_COLONNE-Board.LIM_X)*Board.DIM_X_CELLA,posizione.getY());
		}
		if(posizione.getY()<Board.DIM_Y_CELLA*Board.LIM_Y) {
			posizione.setLocation( posizione.getX(),Board.DIM_Y_CELLA*Board.LIM_Y);
		}
		if(posizione.getY()>(Board.NUMERO_RIGHE-Board.LIM_Y)*Board.DIM_Y_CELLA) {
			posizione.setLocation(posizione.getX(),(Board.NUMERO_RIGHE-Board.LIM_Y)*Board.DIM_Y_CELLA);
		}
	}
}
