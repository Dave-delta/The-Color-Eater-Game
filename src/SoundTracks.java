import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.*;

public class SoundTracks {
	//ATTRIBUTI
	Clip clip[]=new Clip[30];
	File audio[]= new File[30];
	SoundTracks(){	
		audio[0]=new File("gameSoundTracks/backgroundMusic.wav");		//musica sottofondo
		audio[1]=new File("gameSoundTracks/enemyTriggered.wav");		//musica inseguimento
		audio[2]=new File("gameSoundTracks/damage.wav");				//effetto scoperta
		audio[3]=new File("gameSoundTracks/manus.wav");					//effetto manus
		audio[4]=new File("gameSoundTracks/damage.wav");				//effetto danno
		audio[5]=new File("gameSoundTracks/coin.wav");					//effetto coin
		audio[6]=new File("gameSoundTracks/specialcoin.wav");			//effetto specialcoin
		audio[7]=new File("gameSoundTracks/teleport.wav");				//effetto ostacolo;
		audio[8]=new File("gameSoundTracks/damage.wav");				//effetto vittoria;
		audio[9]=new File("gameSoundTracks/damage.wav");				//effetto sconfitta;
		audio[10]=new File("gameSoundTracks/enemyDeath.wav");			//effetto morte nemico
	}
	public void setFile(int i){
			try{
			clip[i] = AudioSystem.getClip();
			clip[i].open(AudioSystem.getAudioInputStream(audio[i]));
		} catch (Exception e){
		}
	}
	public void play(int i) {
		clip[i].start();
	}
	public void loop(int i) {
		clip[i].loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop(int i) {
		clip[i].stop();
	}
}
