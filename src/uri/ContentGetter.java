package uri;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ContentGetter {

    public static void main(String[] args) {
        // Open the URL for reading
        try {
            URL u = new URL("https://www.baidu.com");
            Object o = u.getContent();
            System.out.println("I got a " + o.getClass().getName());
        } catch (MalformedURLException ex) {
            System.err.println(args[0] + " is not a parseable URL");
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}