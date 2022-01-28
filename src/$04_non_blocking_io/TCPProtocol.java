package $04_non_blocking_io;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * @author lmmarise.j@gmail.com
 * @since 2022/1/28 1:36 AM
 */
public interface TCPProtocol {
    void handleAccept(SelectionKey key) throws IOException;
    void handleRead(SelectionKey key) throws IOException;
    void handleWrite(SelectionKey key) throws IOException;
}