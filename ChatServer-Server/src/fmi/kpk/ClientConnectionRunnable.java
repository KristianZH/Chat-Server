package fmi.kpk;

import static fmi.kpk.utils.Constants.CLIENT_LEFT_MESSAGE_FOR_CLIENT;
import static fmi.kpk.utils.Constants.CLIENT_LEFT_MESSAGE_FOR_SERVER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import fmi.kpk.command.Command;
import fmi.kpk.command.CommandAgent;
import fmi.kpk.command.SendAllCommand;

public class ClientConnectionRunnable implements Runnable {

	private String username;
	private Socket socket;

	public ClientConnectionRunnable(String username, Socket socket) {
		this.username = username;
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (!socket.isClosed()) {
				String commandInput = reader.readLine();

				if (commandInput != null) {
					Command cmd = CommandAgent.getCommand(username, commandInput, ChatServer.users);
					if (cmd != null) {
						cmd.exec();
					}
				}
			}
			System.out.println(String.format(CLIENT_LEFT_MESSAGE_FOR_CLIENT, username));
			new SendAllCommand(username, CLIENT_LEFT_MESSAGE_FOR_SERVER, ChatServer.users.values()).exec();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
