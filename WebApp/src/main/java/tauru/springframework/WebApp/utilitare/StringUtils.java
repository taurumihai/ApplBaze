package tauru.springframework.WebApp.utilitare;

public class StringUtils {

    public static Boolean isNullOrEmpty(String string) {

        return (string == null || "".equals(string));
    }
}
