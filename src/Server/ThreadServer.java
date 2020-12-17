package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import gameStruct.BatailleNavale;

public class ThreadServer extends Thread {

	/*
	 * Classe ThreadServer : Discussion avec le client
	 * 
	 * 
	 */

	BufferedReader inPlayer1, inPlayer2;
	PrintWriter outPlayer1, outPlayer2;

	public ThreadServer(Socket client, Socket client2) {
		try {
			inPlayer1 = new BufferedReader(new InputStreamReader(client.getInputStream()));
			outPlayer1 = new PrintWriter(client.getOutputStream(), true);
			inPlayer2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
			outPlayer2 = new PrintWriter(client2.getOutputStream(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void run() {// On synchonized la méthode pour s'assurer que chaque joueur joue bien à son
									// tour
		try {
			// Positionnement des navires
			/*
			 * Nombre de navires : 4 1 Porte-avions (5 cases) 1 Croiseur (4 cases)
			 * 1Contre-torpilleurs (3 cases) 1 Torpilleur (2 cases)
			 * 
			 */
			// Initialisation Joueur 1
			// Les exceptions seront gérées côté client

			outPlayer1.println("POS");
			String posPlayer1 = inPlayer1.readLine();
			outPlayer1.println("WAIT:Patientez, La partie va bientôt commencer");

			// Init Joueur 2
			outPlayer2.println("POS");
			String posPlayer2 = inPlayer2.readLine();
			outPlayer2.println("WAIT:Patientez, La partie va bientôt commencer");

			BatailleNavale game = new BatailleNavale(posPlayer1, posPlayer2);
			boolean hitOrMiss1, hitOrMiss2;
			String targetPlayer1, targetPlayer2;
			boolean isOver = false;
			String isSunk2, isSunk1;
			outPlayer1.println("WAIT:Débutons");
			outPlayer2.println("WAIT:Débutons");

			// TODO Test play
			while (!isOver) {

				// Attaque Joueur 1
				outPlayer1.println("WAIT:A vous de jouer!");
				outPlayer2.println("WAIT:Au tour de l'adversaire");
				outPlayer1.println("SHOOT:");
				targetPlayer1 = inPlayer1.readLine();
				hitOrMiss1 = game.shoot(2, targetPlayer1);
				outPlayer1.println("RESULT:" + hitOrMiss1);
				if (hitOrMiss1) {
					isSunk2 = game.sunk(2);
					if (isSunk2 != null) {
						outPlayer1.println("SUNK:" + isSunk2);
					}
				}

				// Reception joueur 2
				outPlayer2.println("GET:" + targetPlayer1);

				isOver = game.isOver();
				if (isOver) {
					outPlayer1.println("OVER:Partie terminé. Vous avez Gagné");
					outPlayer2.println("OVER:Partie terminé. Vous avez perdu");
					break;
				}

				// Attaque Joueur 2
				outPlayer2.println("WAIT:A vous de jouer!");
				outPlayer1.println("WAIT:Au tour de l'adversaire");
				outPlayer2.println("SHOOT:");
				targetPlayer2 = inPlayer2.readLine();
				hitOrMiss2 = game.shoot(1, targetPlayer2);
				// Resultat attaque
				outPlayer2.println("RESULT:" + hitOrMiss2);
				if (hitOrMiss2) {
					isSunk1 = game.sunk(1);
					if (isSunk1 != null) {
						outPlayer2.println("SUNK:" + isSunk1);
					}
				}

				// Reception dégats joueur 1
				outPlayer1.println("GET:" + targetPlayer2);

				isOver = game.isOver();
				if (isOver) {
					outPlayer2.println("OVER:Partie terminé. Vous avez Gagné");
					outPlayer1.println("OVER:Partie terminé. Vous avez perdu");
					break;
				}
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
		}
	}
}