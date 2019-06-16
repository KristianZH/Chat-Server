package fmi.kpk.command;

import static fmi.kpk.utils.Constants.HelpMenuConstans.DISCONNECT;
import static fmi.kpk.utils.Constants.HelpMenuConstans.SEND_MESSAGE_TO_ALL;
import static fmi.kpk.utils.Constants.HelpMenuConstans.SEND_MESSAGE_TO_USER;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

import fmi.kpk.ChatServer;

public class CommandAgent {

	private static final String SEND_ALL_COMMAND = "send-all";
	private static final String SEND_USER_COMMAND = "send-user";
	private static final String DISCONNECT_COMMAND = "disconnect";
	private static final String SPLIT_PATTERN = "\\s+";

	public static Command getCommand(String username, String commandInput, Map<String, Socket> users)
			throws IOException {
		String[] tokens = commandInput.split(SPLIT_PATTERN);
		String command = tokens[0];
		Socket sender = users.get(username);

		switch (command) {
		case SEND_ALL_COMMAND:
			return new SendAllCommand(username, commandInput.split(SPLIT_PATTERN, 2)[1], users.values());
		case SEND_USER_COMMAND:
			return new SendUserCommand(username, commandInput.split(SPLIT_PATTERN, 3)[2], sender, users.get(tokens[1]));
		case DISCONNECT_COMMAND:
			ChatServer.deleteUser(username);
			return new DisconnectCommand(sender);
		default:
			PrintWriter writer = new PrintWriter(sender.getOutputStream(), true);
			writer.println(printHelpMenu());
			writer.flush();
			return null;
		}
	}

	private static String printHelpMenu() {
		StringBuilder builder = new StringBuilder();
		builder.append("Commands:\n\n");
		builder.append(String.format(SEND_MESSAGE_TO_ALL, SEND_ALL_COMMAND));
		builder.append(String.format(SEND_MESSAGE_TO_USER, SEND_USER_COMMAND));
		builder.append(String.format(DISCONNECT, DISCONNECT_COMMAND));

		return builder.toString();
	}

}
