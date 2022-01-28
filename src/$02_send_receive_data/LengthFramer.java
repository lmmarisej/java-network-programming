package $02_send_receive_data;

import java.io.*;

/**
 * @author lmmarise.j@gmail.com
 * @since 2022/1/27 10:34 PM
 */
public class LengthFramer implements Framer {
    public static final int MAXMESSAGELENGTH = 65535;
    public static final int BYTEMASK = 0xff;
    public static final int SHORTMASK = 0xffff;
    public static final int BYTESHIFT = 8;

    private DataInputStream in; // wrapper for data I/O

    public LengthFramer(InputStream in) throws IOException {
        this.in = new DataInputStream(in);
    }

    public void frameMsg(byte[] message, OutputStream out) throws IOException {
        if (message.length > MAXMESSAGELENGTH) {
            throw new IOException("message too long");
        }
        // write length prefix
        // 大端写入，2个字节来存储长度，先发高位，再发低位，方便使用大端的接收者直接使用short
        out.write((message.length >> BYTESHIFT) & BYTEMASK);    // 只要低[9, 16]位
        out.write(message.length & BYTEMASK);   // 只要低[1, 8]位
        // write message
        out.write(message);
        out.flush();
    }

    public byte[] nextMsg() throws IOException {
        int length;
        try {
            length = in.readUnsignedShort(); // read 2 bytes       读入的虽然是无符号short，但是Java中没有unsigned short，因此使用int来存储
        } catch (EOFException e) { // no (or 1 byte) message
            return null;
        }
        // 0 <= length <= 65535
        byte[] msg = new byte[length];      // 准备存储这个帧的容器
        in.readFully(msg); // if exception, it's a framing error.  一次读取msg大小的数据
        return msg;
    }
}