package $01_basic_socket;

import java.net.*;
import java.util.Enumeration;

/**
 * @author lmmarise.j@gmail.com
 * @since 2022/1/27 3:37 PM
 */
public class InetAddressExample {

    public static void main(String[] args) {

        // Get the network interfaces and associated addresses for this host
        try {
            // 当前主机中每个接口
            Enumeration<NetworkInterface> interfaceList = NetworkInterface.getNetworkInterfaces();
            while (interfaceList.hasMoreElements()) {
                NetworkInterface iface = interfaceList.nextElement();
                // 返回接口的本地名称
                System.out.println("Interface " + iface.getName() + ":");
                // 该接口所关联的每一个地址
                Enumeration<InetAddress> addrList = iface.getInetAddresses();
                if (!addrList.hasMoreElements()) {
                    System.out.println("\t(No addresses for this interface)");
                }
                while (addrList.hasMoreElements()) {
                    InetAddress address = addrList.nextElement();
                    System.out.print("\tAddress "
                            + ((address instanceof Inet4Address ? "(v4)"
                            : (address instanceof Inet6Address ? "(v6)" : "(?)"))));
                    System.out.println(": " + address.getHostAddress());
                }
            }
        } catch (SocketException se) {
            System.out.println("Error getting network interfaces:" + se.getMessage());
        }

        // Get name(s)/address(es) of hosts given on command line
        for (String host : args) {
            try {
                System.out.println(host + ":");
                InetAddress[] addressList = InetAddress.getAllByName(host);
                for (InetAddress address : addressList) {
                    System.out.println("\t" + address.getHostName() + "/" + address.getHostAddress());
                }
            } catch (UnknownHostException e) {
                System.out.println("\tUnable to find address for " + host);
            }
        }
    }
}