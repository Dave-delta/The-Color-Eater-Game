import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;
import javax.swing.*;

public class Board extends JPanel implements KeyListener, ActionListener{
	//questa è la classe che gestisce tutta la parte logica della grafica
	//COSTANTI
	public static int NUMERO_COLONNE=56; 	//3360px
	public static int NUMERO_RIGHE=29;		//1740px	
	public static int SCREEN_X=18;
	public static int SCREEN_Y=12;
	public static int LIM_X=9;
	public static int LIM_Y=6;
	public static int DIM_X_CELLA=60;
	public static int DIM_Y_CELLA=60;
	public static int N_COIN=15;
	public static int N_OSTACOLI=5;
	public static int N_SPECIAL_COIN=1;
	public static int N_ENEMIES=1;
	public static int DAMAGE=1;
	//ATTRIBUTI
	private Player player;
	private Timer updateTick;
	private Timer drawTick;
	private Timer timerCoin;
	private Timer timerSpecialCoin;
	private Timer timerDamage;
	private Timer timerEnemyFury;
	private Stopwatch stopwatch;
	private boolean go=false;
	private Vector<Obstacle> obstacles;
	private Vector<Coin> coins;
	private Vector<SpecialCoin> specialCoins;
	private Vector<Enemy> enemies;
	private Camera camera;
	private Mappa mappa;
	private boolean WPressed, SPressed, DPressed, APressed,MPressed;
	private SoundTracks soundTracks;
	private boolean endGameWin;
	private boolean endGameLost;
	private GamePanels gamePanels;
	private int punteggioFinale;
	private int record;
	//COSTRUTTORE
	public Board() {
		//dimensioni della grafica
		setPreferredSize(new Dimension(SCREEN_X*DIM_X_CELLA,SCREEN_Y*DIM_Y_CELLA));
		//colore di sfondo
		mappa=new Mappa(0,0);
		//inizializzazione del giocatore
		player= new Player(0,NUMERO_RIGHE*DIM_Y_CELLA/2);
		camera=new Camera(SCREEN_X*DIM_X_CELLA/2-(int)(player.getPosizione().getX()), SCREEN_Y*DIM_Y_CELLA/2 -(int)(player.getPosizione().getY()));
		coins=new Vector<Coin>();
		obstacles=new Vector<Obstacle>();
		specialCoins=new Vector<SpecialCoin>();
		enemies=new Vector<Enemy>();
		generaOstacoli();
		updateTick=new Timer(25,this);
		updateTick.start();
		drawTick=new Timer(16,this);
		drawTick.start();
		//ogni 25 millisecondi viene richiamato il metodo actionperformed
		timerCoin=new Timer(5000,this);
		timerCoin.start();
		timerSpecialCoin=new Timer(5000,this);
		timerSpecialCoin.start();
		timerDamage= new Timer(0,this);
		timerEnemyFury=new Timer(15000,this);
		soundTracks= new SoundTracks();
		playBackgroundMusic(0);
		endGameWin=false;
		endGameLost=false;
		gamePanels=new GamePanels(0,0);
		stopwatch=new Stopwatch();
		stopwatch.start();
	} 
	//METODI
	@Override									//tag che specifica alla JVM che è un override
	public void paintComponent(Graphics g) {
		/*
		 * 	il metodo paintCompnent viene richiamato ogni volta che si chiama il metodo repaint()
		 * 	e permette di aggiornare la grafica visualizzata
		 */
		super.paintComponent(g);
		camera.setPosizione(SCREEN_X*DIM_X_CELLA/2-(int)(player.getPosizione().getX()), SCREEN_Y*DIM_Y_CELLA/2 -(int)(player.getPosizione().getY()));
		drawItems(camera.getImage().getGraphics());
		camera.draw(g, this);
		drawStats(g);
		Toolkit.getDefaultToolkit().sync();
	}
	private void drawItems(Graphics g) {
		if(stopwatch.getElapsedTimeSecs()<6) {
			drawGameStartPanel(g);
			return;
		}
		if(endGameWin) {
			drawGameWinPanel(g);
			return;
		}
		if(endGameLost) {
			drawGameLostPanel(g);
			return;
		}
		mappa.draw(g, this);
		player.drawTypeArray(g, this,player.getNDraw());
		for(int i=0;i<coins.size();i++) {
				coins.elementAt(i).draw(g, this);
		}
		for(int i=0;i<obstacles.size();i++) {
			obstacles.elementAt(i).draw(g, this);
		}
		for(int i=0;i<specialCoins.size();i++) {
			specialCoins.elementAt(i).draw(g, this);
		}
		for(int i=0;i<enemies.size();i++) {
			enemies.elementAt(i).drawTypeArray(g, this,enemies.elementAt(i).getNDraw());
			if(enemies.elementAt(i).IsManusActive()) {
				enemies.elementAt(i).getManus().draw(g, this);
			}
		}	
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		if(key==KeyEvent.VK_W) {
			WPressed=true;
		}
		if(key==KeyEvent.VK_S) {
			SPressed=true;	
		}
		if(key==KeyEvent.VK_D) {
			DPressed=true;
		}
		if(key==KeyEvent.VK_A) {
			 APressed=true;
		}
		if(key==KeyEvent.VK_M) {
			MPressed=true;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		int key=e.getKeyCode();
		if(key==KeyEvent.VK_W) {
			WPressed=false;
		}
		if(key==KeyEvent.VK_S) {
			SPressed=false;	
		}
		if(key==KeyEvent.VK_D) {
			DPressed=false;
		}
		if(key==KeyEvent.VK_A) {
			 APressed=false;
		}
	}
	private void keyTick() {
		if(endGameWin || endGameLost || stopwatch.getElapsedTimeSecs()<6) {
			return;
		}
		if(WPressed) {
			player.incrementPosY(-player.getVelocita());
		}
		if(SPressed) {
			player.incrementPosY(player.getVelocita());
		}
		if(DPressed) {
			player.incrementPosX(player.getVelocita());
		}
		if(APressed) {
			player.incrementPosX(-player.getVelocita());
		}
		if(MPressed) {
			if(player.getManusActive()==0) {
				player.activateManus();
				if(player.getAppId()==0) {
					MPressed=false;
				}
			}
		}
		player.calculatePHitBox();
		player.tick();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!endGameWin && !endGameLost && stopwatch.getElapsedTimeSecs()>=6) {
			if(timerCoin==e.getSource()) {
				generaMonete();
				generaEnemy();
			}
			if(timerSpecialCoin==e.getSource()) {
				Random rand=new Random();
				if(rand.nextInt(4)==2) {
					generaSpecialCoin();
				}
			}
			if(timerDamage==e.getSource()) {
				try {
					if(enemies!=null) {
						playSoundEffect(4);
						player.decreasePoints(enemies.elementAt(0).getPunteggio(),enemies.elementAt(0).getDanno());
					}
				}
				catch(Exception error) {}
				
			}
			if(timerEnemyFury==e.getSource()) {
				killEnemy();
			}
			if(updateTick==e.getSource()) {
				specialCoinTick();
				tickForObstacle();
				collectCoin();
				collectSpecialCoin();
				tickForEnemy();
				keyTick();
			}
			endGameTick();
		}
		if(drawTick==e.getSource()) {
			repaint();
		}
	}
	private void generaMonete(){
		Random rand= new Random();
		for(int i=0;i<1+rand.nextInt(5);i++) {
			int x=(rand.nextInt(NUMERO_COLONNE-SCREEN_X-(LIM_X-1))+LIM_X)*DIM_X_CELLA;
			int y=(rand.nextInt(NUMERO_RIGHE-SCREEN_Y-(LIM_Y-1))+LIM_Y)*DIM_Y_CELLA;
			if(checkItem(x,y)) {
				coins.add(new Coin(x,y));
			}
			decreaseTm();
		}
	}
	private void collectCoin() {
		Vector<Coin> coinsToRemove= new Vector<Coin>();
		for(int i=0;i<coins.size();i++) {
			if(player.IsInside(coins.elementAt(i).getHitBox())) {
				coinsToRemove.add(coins.elementAt(i));
				playSoundEffect(5);
				player.increasePoints(coins.elementAt(i).getPunteggio(),coins.elementAt(i).getDanno());
			}
			if(coins.elementAt(i).getTm()<0) {
				coinsToRemove.add(coins.elementAt(i));
			}
		}
		coins.removeAll(coinsToRemove);
	}
	public void decreaseTm() {
		for(int i=0;i<coins.size();i++) {
			coins.elementAt(i).decreaseTm();
		}
	}
	
	private void generaOstacoli() {
		Random rand= new Random();
		for(int i=0;i<N_OSTACOLI;i++) {
			int x=(rand.nextInt(NUMERO_COLONNE-SCREEN_X-(LIM_X-1))+LIM_X)*DIM_X_CELLA;
			int y=(rand.nextInt(NUMERO_RIGHE-SCREEN_Y-(LIM_Y-1))+LIM_Y)*DIM_Y_CELLA;
			if(checkItem(x,y)) {
				obstacles.add(new Obstacle(x,y));
			}		
			else {
				i--;
			}
		}
	}
	private void tickForObstacle() {
		for(int i=0;i<obstacles.size();i++) {
			if(player.IsInside(obstacles.elementAt(i).getHitBox())) {
				playSoundEffect(7);
				player.setPosizione(0, 0);
				playSoundEffect(4);
				player.decreasePoints(obstacles.elementAt(i).getPunteggio(),obstacles.elementAt(i).getDanno());
			}
		}
	}

	private void generaSpecialCoin() {
		Random rand= new Random();
		if(specialCoins.size()<N_SPECIAL_COIN) {
			for(int i=0;i<N_SPECIAL_COIN-specialCoins.size();i++) {
				int x=(rand.nextInt(NUMERO_COLONNE-SCREEN_X-(LIM_X-1))+LIM_X)*DIM_X_CELLA;
				int y=(rand.nextInt(NUMERO_RIGHE-SCREEN_Y-(LIM_Y-1))+LIM_Y)*DIM_Y_CELLA;
				if(checkItem(x,y)) {
					specialCoins.add(new SpecialCoin(x,y));
				}
				else {
					i--;
				}
			}
		}
	}
	private void collectSpecialCoin() {
		Vector<SpecialCoin> coinsToRemove= new Vector<SpecialCoin>();
		for(int i=0;i<specialCoins.size();i++) {
			if(player.IsInside(specialCoins.elementAt(i).getHitBox())) {
				playSoundEffect(6);
				coinsToRemove.add(specialCoins.elementAt(i));
				player.increasePoints(specialCoins.elementAt(i).getPunteggio(),specialCoins.elementAt(i).getDanno());  
				timerSpecialCoin.restart();
			}
		}
		specialCoins.removeAll(coinsToRemove);
	}
	private void specialCoinTick() {
		boolean errore=false;
		for(int i=0;i<specialCoins.size();i++) {
			specialCoins.elementAt(i).tick();
			for(int j=0;j<coins.size();j++) {
				if(specialCoins.elementAt(i).IsInside(coins.elementAt(j).getHitBox())) {
					errore=true;
				}
			}
			if(!errore) {
				for(int j=0;j<obstacles.size();j++) {
					if(specialCoins.elementAt(i).IsInside(obstacles.elementAt(j).getHitBox())) {
						specialCoins.remove(i);
						return;
					}
				}
			}
			if(errore) {
				specialCoins.elementAt(i).direzioneRand();
			}
			specialCoins.elementAt(i).SpecialCoinMove();
		}
	}
	private void generaEnemy() {
		Random rand= new Random();
		if(!player.IsManusActive() || stopwatch.getElapsedTimeSecs()<6) {
			if(enemies.size()<N_ENEMIES) {
				for(int i=0;i<N_ENEMIES-enemies.size();i++) {
					int x=(rand.nextInt(NUMERO_COLONNE-SCREEN_X-(LIM_X-1))+LIM_X)*DIM_X_CELLA;
					int y=(rand.nextInt(NUMERO_RIGHE-SCREEN_Y-(LIM_Y-1))+LIM_Y)*DIM_Y_CELLA;
					if(checkItem(x,y)) {
						enemies.add(new Enemy(x,y));
					}		
					else {
						i--;
					}
				}
			}
		}
	}
	private void tickForEnemy() {
		if(enemies!=null) {
			for(int i=0;i<enemies.size();i++) {
				if(enemies.elementAt(i).getManus()==null) {
					enemies.elementAt(i).enemyMoveTo(player.getPosizione());
					if(!go) {
						timerEnemyFury.start();
						enemies.elementAt(i).activeFury();
						stopBackgroundMusic(0);
						playBackgroundMusic(1);
						go=true;
					}
					
				}
				else if(player.IsInside(enemies.elementAt(i).getRange())){
					enemies.elementAt(i).enemyMoveTo(player.getPosizione());
				}
				else if(enemies.elementAt(i).getDistance(enemies.elementAt(i).getStartPosition())<=enemies.elementAt(i).getDistance(player.getPosizione())) {
					enemies.elementAt(i).enemyMoveTo(enemies.elementAt(i).getStartPosition());
				}
				else {
					enemies.elementAt(i).enemyMoveTo(player.getPosizione());
				}
				if(player.IsInside(enemies.elementAt(i).getHitBox())) {
					timerDamage.setDelay(1000);
					timerDamage.start();
				}
				else {
					timerDamage.stop();
				}
				if(enemies.elementAt(i).getManus()!=null) {
					enemies.elementAt(i).tickForManus();
					if(player.IsInside(enemies.elementAt(i).getManus().getHitBox())) {
						player.setAppId(enemies.elementAt(i).getManus().getId());
						playSoundEffect(3);
						if(player.getAppId()==2) {
							player.activateManus();
						}
						enemies.elementAt(i).setManusNULL();
					}
				}
			}
		}
	}
	private void killEnemy() {
		for(int i=0;i<enemies.size();i++) {
			if(enemies.elementAt(i).getManus()==null) {
				enemies.remove(i);
				stopBackgroundMusic(1);
				playSoundEffect(10);
				playBackgroundMusic(0);
				timerEnemyFury.stop();
			}
		}
	}
	private boolean checkItem(int x,int y) {
		if(x==0 && y==0) {
			return false;
		}
		if(coins!=null) {
			for(int i=0;i<coins.size();i++) {
				if(x==coins.elementAt(i).getPosizione().x && y==coins.elementAt(i).getPosizione().y) {
					return false;
				}
			}
		}
		if(obstacles!=null) {
			for(int i=0;i<obstacles.size();i++) {
				if(x==obstacles.elementAt(i).getPosizione().x && y==obstacles.elementAt(i).getPosizione().y) {
					return false;
				}
			}
		}
		if(specialCoins!=null) {
			for(int i=0;i<specialCoins.size();i++) {
				if(x==specialCoins.elementAt(i).getPosizione().x && y==specialCoins.elementAt(i).getPosizione().y) {
					return false;
				}
			}
		}
		if(enemies!=null) {
			for(int i=0;i<enemies.size();i++) {
				if((x==enemies.elementAt(i).getStartPosition().x && y==enemies.elementAt(i).getStartPosition().y)) {
					return false;
				}
			}
		}
		if(player.IsInside(new Rectangle(x,y,50,50))) {
			return false;
		}
		return true;
	}
	
	private void drawStats(Graphics g) {
		if(endGameLost || stopwatch.getElapsedTimeSecs()<6) {
			return;
		}
		String metricaManus = null;
		String daStampare= "Points:fillfillfil" + player.getPunteggio();
		Graphics2D g2d= (Graphics2D) g;							//da 3d a 2d
		//impostazioni grafiche per il testo
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setColor(new Color(255,0,0));
		g2d.setFont(new Font("Lato", Font.BOLD,20));
		FontMetrics metricaDelFont=g2d.getFontMetrics(g2d.getFont());
		Rectangle rect= new Rectangle(0,DIM_Y_CELLA*(SCREEN_Y-1),DIM_X_CELLA*SCREEN_X,DIM_Y_CELLA);
		int x=(int) (rect.x +(rect.width-metricaDelFont.stringWidth(daStampare)*1.2));
		int y=metricaDelFont.getHeight()+metricaDelFont.getAscent();
		daStampare= "Points:" + player.getPunteggio();
		if(endGameWin) {
			rect.setLocation(0, SCREEN_Y/2*DIM_Y_CELLA);
			daStampare="Punteggio Finale:	"+punteggioFinale;
			g2d.setColor(new Color(11,11,11));
			g2d.setFont(new Font("Lato", Font.BOLD,30));
			x=rect.x+60*3;
			y=rect.y+((rect.height-metricaDelFont.getHeight()))+metricaDelFont.getAscent()+40;
			g2d.drawString(daStampare,x,y);
			daStampare="Record:	"+record;
			y+=40;
			g2d.drawString(daStampare,x,y);
			return;
		}
		
		g2d.drawString(daStampare,x,y);
		daStampare="HP:"+player.getHealth();
		y+=30;
		g2d.drawString(daStampare,x,y);
		if(player.IsManusActive()) {
			switch(player.getAppId()) {
			case 1:
				if(player.getManusActive()!=0)
					metricaManus="Points(X2):"+player.getTimerCount();
				else
					metricaManus="[M] Points(X2)";
				
			break;
			case 2:
				metricaManus="Damages(X2):"+player.getTimerCount();
			break;
			case 3:
				if(player.getManusActive()!=0)
					metricaManus="Every-Heal:"+player.getTimerCount();
				else
					metricaManus="[M] Every-Heal";
			break;
			}
			y+=30;
			g2d.drawString(metricaManus,x,y);
		}
	}
	private void writeFile(){
		String path= "gameFiles/wr.txt";
		try {
			String daInserire=String.valueOf(punteggioFinale);
			File file=new File(path);
			FileWriter fw=new FileWriter(file);
			BufferedWriter bw=new BufferedWriter(fw);
			bw.write(daInserire);
			bw.flush();
			bw.close();
		}
		catch(IOException ioe) {
			System.out.println("Errore scrittura file! "+ioe.getMessage());
		}
	}
	private char[] readFile() {
		String path= "gameFiles/wr.txt";
		char[] in=new char[30];
		int size=0;
		try {
			File file=new File(path);
			FileReader fr=new FileReader(file);
			size=fr.read(in);
			fr.close();
		}
		catch(IOException ioe) {
			System.out.println("Errore lettura file! "+ioe.getMessage());
		}
		char copy[] = Arrays.copyOf(in, size);
		return copy;
	}
	private void endGameTick() {
		if(player.getPunteggio()==player.getMAX_PUNTEGGIO()) {
			stopwatch.stop();
			endGameWin=true;
			stopBackgroundMusic(0);
			playSoundEffect(8);
			calcolaRecord(player.getPunteggio(),(int)stopwatch.getElapsedTimeSecs());
			return;
		}
		if(player.getHealth()==0) {
			stopwatch.stop();
			stopBackgroundMusic(0);
			playSoundEffect(9);
			endGameLost=true;
			return;
		}
	}
	private void playBackgroundMusic(int i) {
		soundTracks.setFile(i);
		soundTracks.play(i);
		soundTracks.loop(i);
	}
	private void stopBackgroundMusic(int i) {
		soundTracks.stop(i);
	}
	private void playSoundEffect(int i) {
		soundTracks.setFile(i);
		soundTracks.play(i);
	}
	private void drawGameStartPanel(Graphics g) {
		player.setPosizione(LIM_X*DIM_X_CELLA, LIM_Y*DIM_Y_CELLA);
		gamePanels.drawTypeArray(g, this,0);
	}
	private void drawGameWinPanel(Graphics g){
		player.setPosizione(LIM_X*DIM_X_CELLA, LIM_Y*DIM_Y_CELLA);
		gamePanels.drawTypeArray(g, this,1);
	}
	private void drawGameLostPanel(Graphics g) {
		player.setPosizione(LIM_X*DIM_X_CELLA, LIM_Y*DIM_Y_CELLA);
		gamePanels.drawTypeArray(g, this,2);
	}
	private void calcolaRecord(int punteggio,int tempo) {
		punteggioFinale=punteggio-(tempo+6);
		if(punteggioFinale<0) {
			punteggioFinale=0;
		}
		try {
			record=Integer.valueOf(new String(readFile()));
		}
		catch(Exception e) {
			record=0;
		}
		if(punteggioFinale>record) {
			writeFile();
			record=punteggioFinale;
		}
	}
}
