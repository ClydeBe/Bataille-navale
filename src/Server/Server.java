package Server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		try (ServerSocket ecoute = new ServerSocket(1500);) {
			System.out.println("Waiting for player");
			while (true) {
				Socket client1 = ecoute.accept();
				Socket client2 = ecoute.accept();
				new ThreadServer(client1, client2).start();
				System.out.println("Partie lancée");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}