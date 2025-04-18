package lk.ijse.gdse.ormcw.util;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
    // Encrypt the password before saving it in the database
    public static String encryptPassword(String password) {

        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Verify the password entered during login
    public static boolean checkPassword(String inputPassword, String hashedPassword) {
        if (hashedPassword == null || hashedPassword.isEmpty()) {
            throw new IllegalArgumentException("Stored password is null or empty");
        }
        return BCrypt.checkpw(inputPassword, hashedPassword);
    }

}
