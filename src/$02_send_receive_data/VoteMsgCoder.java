package $02_send_receive_data;

import java.io.IOException;

/**
 * @author lmmarise.j@gmail.com
 * @since 2022/1/27 11:02 PM
 */
public interface VoteMsgCoder {
    byte[] toWire(VoteMsg msg) throws IOException;
    VoteMsg fromWire(byte[] input) throws IOException;
}
