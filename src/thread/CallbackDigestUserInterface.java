package thread;

import javax.xml.bind.*; // for DatatypeConverter; requires Java 6 or JAXB 1.0

public class CallbackDigestUserInterface {

    public static void receiveDigest(byte[] digest, String name) {
        String result = name + ": " + DatatypeConverter.printHexBinary(digest);
        System.out.println(result);
    }

    public static void main(String[] args) {
        for (String filename : args) {
            // Calculate the digest
            CallbackDigest cb = new CallbackDigest(filename);
            Thread t = new Thread(cb);
            t.start();
        }
    }
}