package nio;

import java.io.*;
import java.net.*;
import java.nio.channels.*;

public class OptionSupport {

    public static void main(String[] args) throws IOException {
        printOptions(SocketChannel.open());
        printOptions(ServerSocketChannel.open());
        printOptions(AsynchronousSocketChannel.open());
        printOptions(AsynchronousServerSocketChannel.open());
        printOptions(DatagramChannel.open());
    }

    private static void printOptions(NetworkChannel channel) throws IOException {
        System.out.println(channel.getClass().getSimpleName() + " supports:");
        for (SocketOption<?> option : channel.supportedOptions()) {
            try {
                System.out.println(option.name() + ": " + channel.getOption(option));
            } catch (Throwable e) {
                System.out.println("Throwable=========>" + e.getMessage());
            }
        }
        System.out.println();
        channel.close();
    }

}
