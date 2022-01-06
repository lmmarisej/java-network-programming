package adress;

import java.net.*;
import java.util.*;

public class InterfaceLister {

    public static void main(String[] args) throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface ni = interfaces.nextElement();
            System.out.println(ni);
            System.out.println(ni.getName());
            System.out.println(ni.getDisplayName());
            Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                System.out.println("\t" + inetAddresses.nextElement());
            }
        }
    }
}
