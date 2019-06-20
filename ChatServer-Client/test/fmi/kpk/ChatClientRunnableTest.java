package fmi.kpk;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChatClientRunnableTest {

	private ClientRunnable client;
	private InputStream is;
	private static final String TEST_COMMAND = "test";

	@Mock
	public Socket socketMock;

	@Before
	public void setUp() throws Exception {
		socketMock = mock(Socket.class);
		client = new ClientRunnable(socketMock);
	}

	@Test
	public void testReadingCommandShouldPassWhenSocketIsOpen() throws IOException {
		is = new ByteArrayInputStream(TEST_COMMAND.getBytes());
		when(socketMock.getInputStream()).thenReturn(is);
		when(socketMock.isClosed()).thenReturn(true);

		client.run();
	}

	@Test
	public void testReadingCommandShouldPassWhenSocketIsClosed() throws IOException {
		is = new ByteArrayInputStream(TEST_COMMAND.getBytes());
		when(socketMock.getInputStream()).thenReturn(is);
		when(socketMock.isClosed()).thenReturn(false);

		client.run();
	}

	@Test(expected = Exception.class)
	public void testReadingCommandShouldThrowExceptionWithIncorectStream() throws IOException {
		is = new ByteArrayInputStream(TEST_COMMAND.getBytes());
		when(socketMock.getInputStream()).thenReturn(null);
		when(socketMock.isClosed()).thenReturn(false);

		client.run();
	}

}
