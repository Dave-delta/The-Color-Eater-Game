import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;



public class Player extends Item implements ActionListener  {
	//COSTANTI
	private static int HITBOX_X=48;
	private static int HITBOX_Y=48;
	private static String IMAGE_NAME[]= {"gameImages/player/color0%.png","gameImages/player/color20%.png","gameImages/player/color40%.png","gameImages/player/color60%.png","gameImages/player/color80%.png","gameImages/player/color100%.png"};
	private static int POINTS=0;
	private static int SPEED=10;
	private static int TIMER_DELAY=1000;
	private static int DELAY_MANUS_1=10;
	private static int DELAY_MANUS_2=10;
	private static int DELAY_MANUS_3=10;
	private static int MAX_HEALTH=15;
	private static int DAMAGE=0;
	//ATTRIBUTI
	private int TimerCount=0;
	private int manusActive;
	private int AppID;
	private Timer timerManus;
	private int health;
	
	//COSTRUTTORI
	public Player(int x,int y) {
		super(x,y,HITBOX_X,HITBOX_Y,IMAGE_NAME,POINTS,SPEED,DAMAGE);
		health=MAX_HEALTH;
		timerManus=new Timer(TIMER_DELAY, this);
		manusActive=0;
		AppID=0;
	}
	//METODI
	public void setHealth(int health) {
		if(health>MAX_HEALTH) {
			this.health=MAX_HEALTH;
		}
		else if(health<0) {
			this.health=0;
		}
		else {
			this.health=health;
		}
	}
	public int getHealth() {return health;}
	public int getMAX_HEALTH() {return MAX_HEALTH;}
	public void decreasePoints(int points,int hp) {
		switch(manusActive) {
			case 2:
				setPunteggio(getPunteggio()-points*2);
				setHealth(getHealth()-hp*2);
			break;
			default:
				setPunteggio(getPunteggio()-points);
				setHealth(getHealth()-hp);
			break;
		}
	}
	public void increasePoints(int points,int hp) {
		switch(manusActive) {
			case 1:
				setPunteggio(getPunteggio()+points*2);
			break;
			case 3:
				setHealth(getHealth()+hp);
			break;
			default:
				setPunteggio(getPunteggio()+points);
			break;
		}
	}
	public int getManusActive() {return manusActive;}
	public void setManusActive(int manusActive) {this.manusActive=manusActive;}
	public int getAppId() {return AppID;}
	public void setAppId(int id) {this.AppID=id;}
	public boolean IsManusActive() {
			return manusActive!=0 || AppID!=0;
	}
	public int getTimerCount() {return TimerCount;}
	
	public void activateManus() {
		switch(AppID) {
		case 1:
			manusActive=AppID;
			TimerCount=DELAY_MANUS_1;
			timerManus.start();
		break;
		case 2:
			manusActive=AppID;
			TimerCount=DELAY_MANUS_2;
			timerManus.start();
		break;
		case 3:
			manusActive=AppID;
			TimerCount=DELAY_MANUS_3;
			timerManus.start();
		break;
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(timerManus==e.getSource()) {
			if(TimerCount==0) {
				manusActive=0;
				AppID=0;
				timerManus.stop();
			}
			else
				TimerCount-=TIMER_DELAY/1000;
		}
	}
	public int getNDraw() {
		if(punteggio<1000) {
			return 0;
		}
		if(punteggio<2000) {
			return 1;
		}
		if(punteggio<3000) {
			return 2;
		}
		if(punteggio<4000) {
			return 3;
		}
		if(punteggio<5000) {
			return 4;
		}
		if(punteggio<=6000) {
			return 5;
		}
		return -1;
	}
}
