package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		// Try with ressources pour la fermeture automatique des ressources
		// Permet de factoriser considérablement le code
		try (Socket s = new Socket("127.0.0.1", 1500);
				PrintWriter out = new PrintWriter(s.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader((s.getInputStream())));) {
			ListenningThreadClient lsThread = new ListenningThreadClient(s);
			lsThread.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
