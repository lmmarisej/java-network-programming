package socket;

import java.net.*;
import java.io.*;
import java.util.Date;

public class MultithreadedDaytimeServer {

    public final static int PORT = 13;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                try {
                    Socket connection = server.accept();
                    Thread task = new DaytimeThread(connection);
                    task.start();
                } catch (IOException ex) {
                }
            }
        } catch (IOException ex) {
            System.err.println("Couldn't start server");
        }
    }

    private static class DaytimeThread extends Thread {

        private final Socket connection;

        DaytimeThread(Socket connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            try {
                Writer out = new OutputStreamWriter(connection.getOutputStream());
                Date now = new Date();
                out.write(now + "酵\r\n");
                out.flush();
            } catch (IOException ex) {
                System.err.println(ex);
            } finally {
                try {
                    connection.close();
                } catch (IOException e) {
                    // ignore;
                }
            }
        }
    }
}

class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", MultithreadedDaytimeServer.PORT);
        InputStreamReader reader = new InputStreamReader(socket.getInputStream());
        while (reader.ready()) {
            int i = reader.read();
            System.out.println(i  + ":" + (char) i);
        }
    }
}