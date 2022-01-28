package $03_thread;

import $02_send_receive_data.VoteMsg;
import $02_send_receive_data.VoteMsgTextCoder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

/**
 * @author lmmarise.j@gmail.com
 * @since 2022/1/27 11:54 PM
 */
public class VoteMulticastReceiver {

    public static void main(String[] args) throws IOException {

        if (args.length != 2) { // Test for correct # of args
            throw new IllegalArgumentException("Parameter(s): <Multicast Addr> <Port>");
        }

        InetAddress address = InetAddress.getByName(args[0]); // Multicast address
        if (!address.isMulticastAddress()) { // Test if multicast address
            throw new IllegalArgumentException("Not a multicast address");
        }

        int port = Integer.parseInt(args[1]); // Multicast port
        MulticastSocket sock = new MulticastSocket(port); // for receiving
        // 多个接收者表名希望从哪个多播地址接收数据来加入多播组
        sock.joinGroup(address); // Join the multicast group

        VoteMsgTextCoder coder = new VoteMsgTextCoder();

        // Receive a datagram
        DatagramPacket packet = new DatagramPacket(new byte[VoteMsgTextCoder.MAX_WIRE_LENGTH],
                VoteMsgTextCoder.MAX_WIRE_LENGTH);
        sock.receive(packet);

        VoteMsg vote = coder.fromWire(Arrays.copyOfRange(packet.getData(), 0, packet
                .getLength()));

        System.out.println("Received Text-Encoded Request (" + packet.getLength()
                + " bytes): ");
        System.out.println(vote);

        sock.close();
    }
}