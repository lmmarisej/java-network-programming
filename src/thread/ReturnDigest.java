package thread;

import java.io.*;
import java.security.*;

@SuppressWarnings("all")
public class ReturnDigest extends Thread {

    private final String filename;
    private byte[] digest;

    public ReturnDigest(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        try {
            FileInputStream in = new FileInputStream(filename);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream din = new DigestInputStream(in, sha);
            while (din.read() != -1) ; // read entire file
            din.close();
            digest = sha.digest();      // 线程执行完成，将修改的结果放入实例变量digest，供其它线程读取
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (NoSuchAlgorithmException ex) {
            System.err.println(ex);
        }
    }

    public byte[] getDigest() {
        return digest;
    }
}