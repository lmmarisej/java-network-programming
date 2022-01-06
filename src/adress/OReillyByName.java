package adress;

import java.net.*;

public class OReillyByName {

    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName("www.oreilly.com");
            System.out.println(address);
            InetAddress[] allByName = InetAddress.getAllByName("www.oreilly.com");
            for (InetAddress inetAddress : allByName) {
                System.out.println(inetAddress);
            }
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println(localHost);

            InetAddress byName = InetAddress.getByName("www.google.com");
            System.out.println(byName);

            new SecurityManager().checkConnect("104.112.69.59", -1);
        } catch (UnknownHostException ex) {
            System.out.println("Could not find www.oreilly.com");
        }
    }
}
