import com.vcipher.crypto.CryptoManager;

public class TestRunner {
    public static void main(String[] args) {
        try {
            CryptoManager crypto = new CryptoManager();
            String message = "Hello world! I am inevitable";
            String password = "123";
            String encrypted = crypto.encrypt(message, password);
            System.out.println("(message = " + message + ", password = " + password + ") := " + encrypted);
            String decrypted = crypto.decrypt(encrypted, password);
            System.out.println("(encrypted = " + encrypted + ", password = " + password + ") := " + decrypted);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}