package thread;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SuppressWarnings("all")
public class DigestThread extends Thread {

    private final String filename;

    public DigestThread(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        try {
            FileInputStream in = new FileInputStream(filename);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream din = new DigestInputStream(in, sha);
            while (din.read() != -1) ;
            din.close();
            byte[] digest = sha.digest();

            StringBuilder result = new StringBuilder(filename);
            result.append(": ");
            result.append(DatatypeConverter.printHexBinary(digest));
            System.out.println(result);
        } catch (IOException | NoSuchAlgorithmException ex) {
            System.err.println(ex);
        }
    }

    public static void main(String[] args) {
        args = new String[]{"/Users/lmmarise.j/Desktop/jnp4examples/src/Daytime.java"};
        for (String filename : args) {
            Thread t = new DigestThread(filename);
            t.start();
        }
    }
}