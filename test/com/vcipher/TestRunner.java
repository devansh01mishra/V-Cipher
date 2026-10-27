import com.vcipher.crypto.CryptoManager;
import com.vcipher.stego.BitPacker;

public class TestRunner {
    public static void main(String[] args) {
        try {
            String message = "Hello world! I am inevitable";
            String password = "123";
            
            CryptoManager crypto = new CryptoManager();
            String encrypted = crypto.encrypt(message, password);
            System.out.println("(message = " + message + ", password = " + password + ") := " + encrypted);
            
            BitPacker packer = new BitPacker();
            int[] encryptedInBits = packer.getBits(encrypted);
            String encryptedRestored = packer.restore(encryptedInBits);
            
            String decrypted = crypto.decrypt(encryptedRestored, password);
            System.out.println("(encrypted = " + encryptedRestored + ", password = " + password + ") := " + decrypted);
        
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}