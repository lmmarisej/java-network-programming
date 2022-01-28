package $02_send_receive_data;

import java.io.*;

/**
 * @author lmmarise.j@gmail.com
 * @since 2022/1/27 10:32 PM
 */
public class DelimFramer implements Framer {

    private InputStream in;        // data source
    private static final byte DELIMITER = '\n'; // message delimiter

    public DelimFramer(InputStream in) {
        this.in = in;
    }

    public void frameMsg(byte[] message, OutputStream out) throws IOException {
        // ensure that the message does not contain the delimiter
        for (byte b : message) {
            if (b == DELIMITER) {
                throw new IOException("Message contains delimiter");
            }
        }
        out.write(message);
        out.write(DELIMITER);
        out.flush();
    }

    public byte[] nextMsg() throws IOException {
        ByteArrayOutputStream messageBuffer = new ByteArrayOutputStream();
        int nextByte;

        // fetch bytes until find delimiter
        while ((nextByte = in.read()) != DELIMITER) {
            if (nextByte == -1) { // end of stream?
                if (messageBuffer.size() == 0) { // if no byte read
                    return null;
                } else { // if bytes followed by end of stream: framing error
                    throw new EOFException("Non-empty message without delimiter");
                }
            }
            messageBuffer.write(nextByte); // write byte to buffer
        }

        return messageBuffer.toByteArray();
    }
}