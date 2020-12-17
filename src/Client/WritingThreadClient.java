package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class WritingThreadClient {
	private PrintWriter out;

	public WritingThreadClient(Socket s) throws IOException {
		out = new PrintWriter(s.getOutputStream(), true);
	}

	public void run(String s) {
		out.println(s);
	}
}
