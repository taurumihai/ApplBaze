package tauru.springframework.WebApp.utilitare;

public class StringUtils {

    public static Boolean isNullOrEmpty(String string) {

        return (string == null || "".equals(string));
    }

    public static String getUserName(String firstName) {

        if (firstName != null) {
            return firstName;
        } else {
            return "";
        }
    }
}
