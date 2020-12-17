package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import gameStruct.BatailleNavaleClient;

public class ListenningThreadClient extends Thread {
	private BufferedReader in;
	private BatailleNavaleClient bataille;
	private boolean isOver;
	private WritingThreadClient sendServer;
	private String shootedCase = "";

	public ListenningThreadClient(Socket s) throws IOException {
		// TODO Auto-generated constructor stub(Socket s) throws IOException {
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		bataille = new BatailleNavaleClient();
		isOver = false;
		sendServer = new WritingThreadClient(s);
	}

	private void executeMessages(String message) throws IOException {
		/*
		 * Implémentation du protocole de communication : Traitement des messages
		 * entrants
		 */

		// Interpretation des messages serveurs WAIT suivi d'un message à afficher au
		// client POS Initialisation de la position du joueur
		// GET Recevoir l'attaque de l'adversaire
		// SHOOT attaquer
		// OVER partie terminée
		// RESULT resultat du shootde l'adversaire

		String[] messageTable = message.split(":");

		switch (messageTable[0]) {
		case "WAIT":
			System.out.println(messageTable[1]);
			break;
		case "POS":
			sendServer.run(bataille.getMyPos());
			break;
		case "GET":
			bataille.receiveShootAndUpdateGrid(messageTable[1]);
			break;
		case "SHOOT":
			shootedCase = bataille.shoot();
			sendServer.run(shootedCase);
			break;
		case "RESULT":
			boolean result = messageTable[1].equals("true") ? true : false;
			System.out.println(result == true ? "Touché" : "Loupé");
			bataille.updateOpponentGrid(shootedCase, result);
			break;
		case "OVER":
			System.out.println(messageTable[1]);
			isOver = true;
			break;
		case "SUNK":
			System.out.println(messageTable[1]);
			break;
		default:
			// Jamais satisfait
			throw new IllegalArgumentException("Unexpected value: " + messageTable[0]);
		}
	}

	public void run() {
		try {
			while (!isOver) {
				executeMessages(in.readLine());
			}
		} catch (IOException e) {
		}
	}
}
