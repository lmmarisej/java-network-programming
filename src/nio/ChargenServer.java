package nio;

import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.util.*;
import java.io.IOException;

public class ChargenServer {

    public static int DEFAULT_PORT = 19;

    public static void main(String[] args) {

        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (RuntimeException ex) {
            port = DEFAULT_PORT;
        }
        System.out.println("Listening for connections on port " + port);

        byte[] rotation = new byte[95 * 2];
        for (byte i = ' '; i <= '~'; i++) {
            rotation[i - ' '] = i;
            rotation[i + 95 - ' '] = i;
        }

        ServerSocketChannel serverChannel;
        Selector selector;
        try {
            serverChannel = ServerSocketChannel.open();
            ServerSocket ss = serverChannel.socket();
            InetSocketAddress address = new InetSocketAddress(port);
            ss.bind(address);
            // 阻塞式除了能用buffer外，对比普通套接字没有任何优点，因此总是将信道设置为非阻塞式的
            serverChannel.configureBlocking(false);
            selector = Selector.open();     // 创建
            // 注意：一个信道还能注册多个selector，也就能处理多种SelectionKey
            // SelectionKey是位向量，也就可以通过or构造一个位向量来指定一组操作
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);       // 将选择器注册到想要监控的信道上——serverChannel
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        while (true) {
            try {
                // 任何对Channel所关联事件的改变，都只在下次调用select方法后才会生效
                selector.select();      // 阻塞等待serverChannel上OP_ACCEPT事件
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }

            Set<SelectionKey> readyKeys = selector.selectedKeys();      // selector上发生了感兴趣的事件
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()) {

                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    if (key.isAcceptable()) {       // channel准备好接收新socket，就将其注册到selector
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();     // 不会阻塞，因为已经准备好了
                        System.out.println("Accepted connection from " + client);
                        client.configureBlocking(false);
                        SelectionKey key2 = client.register(selector, SelectionKey.OP_WRITE);       // channel注册到selector
                        ByteBuffer buffer = ByteBuffer.allocate(74);        // 存储数据的容器
                        buffer.put(rotation, 0, 72);
                        buffer.put((byte) '\r');
                        buffer.put((byte) '\n');
                        buffer.flip();
                        key2.attach(buffer);        // 将该容器附加到SelectionKey
                    } else if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();      // 取出附件
                        if (!buffer.hasRemaining()) {
                            // Refill the buffer with the next line
                            buffer.rewind();
                            // Get the old first character
                            int first = buffer.get();
                            // Get ready to change the data in the buffer
                            buffer.rewind();
                            // Find the new first characters position in rotation
                            int position = first - ' ' + 1;
                            // copy the data from rotation into the buffer
                            buffer.put(rotation, position, 72);
                            // Store a line break at the end of the buffer
                            buffer.put((byte) '\r');
                            buffer.put((byte) '\n');
                            // Prepare the buffer for writing
                            buffer.flip();
                        }
                        client.write(buffer);
                    }
                } catch (IOException ex) {
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }
    }
}