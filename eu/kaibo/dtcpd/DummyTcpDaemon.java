package eu.kaibo.dtcpd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DummyTcpDaemon {

	private static final Logger logger = Logger.getLogger(DummyTcpDaemon.class.getName());

	public static void main(final String[] args) {

		try {
			int listenPort = 0;
			listenPort = Integer.parseInt(args[0]);
			final ServerSocket socket = new ServerSocket(listenPort);
			final Socket client = socket.accept();
			final BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			final String s = br.readLine();
			final String addr = client.getInetAddress().getHostAddress();
			final int port = client.getPort();
			if (s != null)
				bw.write(s);
			bw.flush();
			br.close();
			bw.close();
			client.close();
			socket.close();
			logger.log(Level.INFO, "Received {0} from client {1}:{2}", new Object[] { s, addr, port});
		} catch (final IOException e) {
			logger.log(Level.SEVERE, null, e);
		} catch (final NumberFormatException e) {
			logger.log(Level.SEVERE, null, e);
		} catch (final ArrayIndexOutOfBoundsException e) {
			logger.log(Level.SEVERE, "Please specify port to listen to as the first parameter");
		}

	}
}