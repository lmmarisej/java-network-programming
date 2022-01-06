package udp;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

class SenderThread extends Thread {

    private final InetAddress server;
    private final DatagramSocket socket;
    private final int port;
    private volatile boolean stopped = false;

    SenderThread(DatagramSocket socket, InetAddress address, int port) {
        this.server = address;
        this.port = port;
        this.socket = socket;
        this.socket.connect(server, port);
    }

    public void halt() {
        this.stopped = true;
    }

    @Override
    public void run() {
        try {
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                if (stopped) return;
                String theLine = userInput.readLine();
                if (theLine.equals(".")) break;
                byte[] data = theLine.getBytes(StandardCharsets.UTF_8);
                DatagramPacket output
                        = new DatagramPacket(data, data.length, server, port);
                socket.send(output);
                Thread.yield();
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}