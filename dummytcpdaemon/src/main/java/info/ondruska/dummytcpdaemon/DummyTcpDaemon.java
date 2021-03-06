package info.ondruska.dummytcpdaemon;

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

  private static final Logger LOGGER = Logger.getLogger(DummyTcpDaemon.class.getName());

  public static void main(String[] args) {

    try {
      int listenPort = 0;
      listenPort = Integer.parseInt(args[0]);
      try (ServerSocket socket = new ServerSocket(listenPort); Socket client = socket.accept(); BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream())); BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));) {
        String s = br.readLine();
        String addr = client.getInetAddress().getHostAddress();
        int port = client.getPort();
        if (s != null) {
          bw.write(s);
        }
        bw.flush();
        LOGGER.log(Level.INFO, "Received {0} from client {1}:{2}", new Object[]{s, addr, port});
      }
    } catch (IOException | NumberFormatException e) {
      LOGGER.log(Level.SEVERE, null, e);
    } catch (ArrayIndexOutOfBoundsException e) {
      LOGGER.log(Level.SEVERE, "Please specify port to listen to as the first parameter");
    }

  }
}
