package uri;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class SourceViewer {

    public static void main(String[] args) {
        InputStream in = null;
        try {
            // Open the URL for reading
            URL u = new URL("https://www.baidu.com");
            in = u.openStream();
            // buffer the input to increase performance
            in = new BufferedInputStream(in);
            // chain the InputStream to a Reader
            InputStreamReader r = new InputStreamReader(in);
            char[] buf = new char[1024 * 8];
            while (r.read(buf) != -1) {
                System.out.print(buf);
            }
        } catch (MalformedURLException ex) {
            System.err.println(args[0] + " is not a parseable URL");
        } catch (IOException ex) {
            System.err.println(ex);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
} 