package fmi.kpk;

import static fmi.kpk.utils.Constants.DISCONNECT_MESSAGE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientRunnable implements Runnable {
	private Socket socket;

	public ClientRunnable(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (!socket.isClosed()) {
				String message = reader.readLine();
				if (message != null) {
					System.out.println(message);
				} else {
					System.out.println(DISCONNECT_MESSAGE);
					return;
				}
			}
		} catch (IOException e) {
			System.out.println(DISCONNECT_MESSAGE);
			if (!socket.isClosed()) {
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}