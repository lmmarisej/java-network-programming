import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class EncoderTest {

    public static void main(String[] args) {
        System.out.println(URLEncoder.encode("This string has spaces", StandardCharsets.UTF_8));
        System.out.println(URLEncoder.encode("This*string*has*asterisks", StandardCharsets.UTF_8));
        System.out.println(URLEncoder.encode("This%string%has%percent%signs", StandardCharsets.UTF_8));
        System.out.println(URLEncoder.encode("This+string+has+pluses", StandardCharsets.UTF_8));
        System.out.println(URLEncoder.encode("This/string/has/slashes", StandardCharsets.UTF_8));
        System.out.println(URLEncoder.encode("This\"string\"has\"quote\"marks", StandardCharsets.UTF_8));
        System.out.println(URLEncoder.encode("This:string:has:colons", StandardCharsets.UTF_8));
        System.out.println(URLEncoder.encode("This~string~has~tildes", StandardCharsets.UTF_8));
        System.out.println(URLEncoder.encode("This(string)has(parentheses)", StandardCharsets.UTF_8));
        System.out.println(URLEncoder.encode("This.string.has.periods", StandardCharsets.UTF_8));
        System.out.println(URLEncoder.encode("This=string=has=equals=signs", StandardCharsets.UTF_8));
        System.out.println(URLEncoder.encode("This&string&has&ampersands", StandardCharsets.UTF_8));
        System.out.println(URLEncoder.encode("Thiséstringéhasé non - ASCII characters", StandardCharsets.UTF_8));
    }
}