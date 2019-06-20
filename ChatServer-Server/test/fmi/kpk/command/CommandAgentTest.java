package fmi.kpk.command;

import static fmi.kpk.utils.Constants.Commands.DISCONNECT;
import static fmi.kpk.utils.Constants.Commands.SEND_ALL;
import static fmi.kpk.utils.Constants.Commands.SEND_USER;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collections;

import org.junit.Test;

public class CommandAgentTest {

	private static final String TEST_USERNAME = "user";
	private final static String SEND_ALL_MESSAGE = SEND_ALL + " Hello";
	private final static String SEND_USER_MESSAGE = SEND_USER + " user hello";

	@Test
	public void testCommandDisconnectShouldReturnDisconnectName() throws IOException {
		String expected = DISCONNECT;
		String actual = CommandAgent.getCommand(TEST_USERNAME, DISCONNECT, Collections.emptyMap()).getName();

		assertEquals(expected, actual);
	}

	@Test
	public void testCommandSendUserShouldReturnSendUserName() throws IOException {
		String expected = SEND_USER;
		String actual = CommandAgent.getCommand(TEST_USERNAME, SEND_USER_MESSAGE, Collections.emptyMap()).getName();

		assertEquals(expected, actual);
	}

	@Test
	public void testCommandSendAllShouldReturnSendAllName() throws IOException {
		String expected = SEND_ALL;
		String actual = CommandAgent.getCommand(TEST_USERNAME, SEND_ALL_MESSAGE, Collections.emptyMap()).getName();

		assertEquals(expected, actual);
	}

}
