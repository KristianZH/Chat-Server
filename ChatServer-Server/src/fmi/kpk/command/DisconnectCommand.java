package fmi.kpk.command;

import static fmi.kpk.utils.Constants.CLIENT_DISCONNECT_MESSAGE;
import static fmi.kpk.utils.Constants.Commands.DISCONNECT;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class DisconnectCommand implements Command {
	private Socket user;

	public DisconnectCommand(Socket user) {
		this.user = user;
	}

	@Override
	public synchronized void exec() throws IOException {
		PrintWriter writer = new PrintWriter(user.getOutputStream(), true);
		writer.println(CLIENT_DISCONNECT_MESSAGE);
		user.close();
	}

	@Override
	public String getName() {
		return DISCONNECT;
	}
}
