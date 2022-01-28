package $02_send_receive_data;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author lmmarise.j@gmail.com
 * @since 2022/1/27 10:29 PM
 */
public interface Framer {
    // 每次向输出流写入一个帧
    void frameMsg(byte[] message, OutputStream out) throws IOException;

    // 根据输入流获取一个帧
    byte[] nextMsg() throws IOException;
}