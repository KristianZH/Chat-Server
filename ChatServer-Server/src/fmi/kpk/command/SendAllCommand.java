package fmi.kpk.command;

import static fmi.kpk.utils.Constants.Commands.SEND_ALL;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collection;

public class SendAllCommand implements Command {
	private String username;
	private String message;
	private Collection<Socket> users;

	public SendAllCommand(String username, String message, Collection<Socket> users) {
		this.message = message;
		this.users = users;
		this.username = username;
	}

	@Override
	public synchronized void exec() throws IOException {
		for (Socket toUser : users) {
			if (toUser != null) {
				PrintWriter toWriter = new PrintWriter(toUser.getOutputStream(), true);
				toWriter.println(String.format("[%s]: %s", username, message));
				toWriter.flush();
			}
		}
	}

	@Override
	public String getName() {
		return SEND_ALL;
	}

}
