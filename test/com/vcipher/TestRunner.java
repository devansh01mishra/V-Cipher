import com.vcipher.crypto.CryptoManager;

public class TestRunner {
    public static void main(String[] args) {
        try {
            CryptoManager crypto = new CryptoManager();
            String message = "Hello world";
            String password = "123";
            String encrypted = crypto.encrypt(message.getBytes(), password).toString();
            System.out.println("(message = " + message + ", password = " + password + ") := " + encrypted);
            String decrypted = crypto.decrypt(encrypted.getBytes(), password).toString();
            System.out.println("(encrypted = " + encrypted + ", password = " + password + ") := " + decrypted);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}