package fmi.kpk.command;

import static fmi.kpk.utils.Constants.Commands.SEND_USER;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SendUserCommand implements Command {
	private String username;
	private String message;
	private Socket fromUser;
	private Socket toUser;

	public SendUserCommand(String username, String message, Socket fromUser, Socket toUser) {
		this.username = username;
		this.message = message;
		this.fromUser = fromUser;
		this.toUser = toUser;
	}

	@Override
	public synchronized void exec() throws IOException {
		if (toUser != null) {
			PrintWriter fromWriter = new PrintWriter(fromUser.getOutputStream(), true);
			fromWriter.println(String.format("[%s]: %s", username, message));
			fromWriter.flush();

			PrintWriter toWriter = new PrintWriter(toUser.getOutputStream(), true);
			toWriter.println(String.format("[%s]: %s", username, message));
			toWriter.flush();
		}
	}

	@Override
	public String getName() {
		return SEND_USER;
	}

}
