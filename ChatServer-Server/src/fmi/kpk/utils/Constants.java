package fmi.kpk.utils;

public class Constants {

	private Constants() {
		// EMPTY BODY
	}

	public static final String SERVER_STARTED_MESSAGE = "server is running on localhost:%d%n";

	public static final String CLIENT_CONNECTED_MESSAGE = " connected";

	public static final String CLIENT_JOIN_NOTIFY_MESSAGE = "has joined the room";

	public static final String CLIENT_DISCONNECT_MESSAGE = "You have been disconnected from the server";

	public static final String CLIENT_LEFT_MESSAGE_FOR_CLIENT = "%s has left the room";

	public static final String CLIENT_LEFT_MESSAGE_FOR_SERVER = "has left the room";

	public static final String SUCH_USER_EXISTS_MESSAGE = "User with this name already exists";

	public class HelpMenuConstans {

		public static final String SEND_MESSAGE_TO_ALL = "%s : Send message to all users\n";

		public static final String SEND_MESSAGE_TO_USER = "%s : Send message to user\n";

		public static final String DISCONNECT = "%s : Disconect from the server\n";
	}

	public class Commands {

		public static final String DISCONNECT = "disconnect";

		public static final String SEND_ALL = "send-all";

		public static final String SEND_USER = "send-user";
	}
}
