package fmi.kpk;

import static fmi.kpk.utils.Constants.CLIENT_CONNECTED_MESSAGE;
import static fmi.kpk.utils.Constants.CLIENT_JOIN_NOTIFY_MESSAGE;
import static fmi.kpk.utils.Constants.SERVER_STARTED_MESSAGE;
import static fmi.kpk.utils.Constants.SUCH_USER_EXISTS_MESSAGE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import fmi.kpk.command.SendAllCommand;

public class ChatServer {
	private static int PORT = 4444;

	public static Map<String, Socket> users = new ConcurrentHashMap<>();

	public static void deleteUser(String username) {
		users.remove(username);
	}

	public static void main(String[] args) {
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			System.out.printf(SERVER_STARTED_MESSAGE, PORT);
			try {
				while (true) {
					Socket socket = serverSocket.accept();
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String username = reader.readLine();
					if (!validateUser(username, socket)) {
						continue;
					}

					users.put(username, socket);
					System.out.println(username + CLIENT_CONNECTED_MESSAGE);
					new SendAllCommand(username, CLIENT_JOIN_NOTIFY_MESSAGE, users.values()).exec();

					ClientConnectionRunnable runnable = new ClientConnectionRunnable(username, socket);
					new Thread(runnable).start();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean validateUser(String user, Socket userSocket) throws IOException {
		if (users.containsKey(user)) {
			PrintWriter toWriter = new PrintWriter(userSocket.getOutputStream(), true);
			toWriter.println(String.format(SUCH_USER_EXISTS_MESSAGE));
			toWriter.flush();

			return false;
		}

		return true;
	}

}