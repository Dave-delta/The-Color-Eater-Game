/*
	 * GRAFICA CON JSWING
	 * 
	 * java fornisce il pacchetto grafico switng
	 * 
	 * per creare una finestra realizziamo un metodo privato della classe App che istanzia tutte
	 * le funzionalità necessarie per fare funzionare bene una finestra sulla JVA
	 */
import javax.swing.*;
public class App {
	//metodo statico che permette di creare la finestra
	private static void initWindow() {													
		JFrame window = new JFrame("The Color-Eater");							//creazione della finestra e settaggio del titolo
		Board board= new Board();												//creazione istanza di Board
		window.add(board);														//Aggiungiamo il pannello board alla grafica
		window.addKeyListener(board);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);					//impostazione di chiusura
		window.setResizable(false);												//blocco resize della finstra
		window.pack();															//mostra la finstra delle dimensioni impostate
		window.setLocationRelativeTo(null);										//posizione centrale
		window.setVisible(true);												//rendere visibile la finestra
	}
	public static void main(String[] args) {
		//JFRame mette a disponsizione degli strumenti per ottimizzare 
		// e rendre compatibile la grafica create con ilresto delle grafiche
		// nella macchina in cui vengono eseguite
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initWindow(); //richiamiamo la funzione per creare la finestra
			}
		} );		
	}
}
