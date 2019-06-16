package fmi.kpk;

import static fmi.kpk.utils.Constants.ASK_FOR_USERNAME_MESSAGE;
import static fmi.kpk.utils.Constants.CAN_NOT_CONNECT_TO_SERVER_MESSAGE;
import static fmi.kpk.utils.Constants.DISCONNECT_MESSAGE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
	private static final String SERVER_HOST = "localhost";
	private static final int SERVER_PORT = 4444;
	private Socket socket;
	private PrintWriter writer;

	public static void main(String[] args) throws IOException {
		new ChatClient().run();
	}

	public void run() throws IOException {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println(ASK_FOR_USERNAME_MESSAGE);
			String username = scanner.nextLine();
			connect(SERVER_HOST, SERVER_PORT, username);

			boolean shoudStop = false;
			while (!shoudStop) {
				String input = scanner.nextLine();
				writer.println(input);
				if (input.equals(DISCONNECT_MESSAGE)) {
					socket.close();
					shoudStop = true;
				}
			}
		}
	}

	private void connect(String host, int port, String username) {
		try {
			socket = new Socket(host, port);
			writer = new PrintWriter(socket.getOutputStream(), true);
			new BufferedReader(new InputStreamReader(socket.getInputStream()));

			writer.println(username);

			ClientRunnable clientRunnable = new ClientRunnable(socket);
			new Thread(clientRunnable).start();
		} catch (IOException e) {
			System.out.println(CAN_NOT_CONNECT_TO_SERVER_MESSAGE);
		}
	}
}