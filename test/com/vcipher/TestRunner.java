import com.vcipher.crypto.CryptoManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import com.vcipher.io.FileManager;
import com.vcipher.io.ServiceManager;
import com.vcipher.stego.BitPacker;
import com.vcipher.stego.SteganoManager;
import com.vcipher.ui.ThemeConfig;
import com.vcipher.utils.Coordinates;
import java.awt.Color;
import javax.swing.JPanel;

public class TestRunner {
    public static void main(String[] args) throws Exception {
        testCoordinatesModule();
        testBitPackerModule();
        testCryptoModule();
        testSteganoModule();
        testFileManagerModule();
        testServiceManagerModule();
        testThemeConfigModule();
        testCryptoAndSteganoIntegration();
        System.out.println("All tests passed.");
    }

    private static void testCoordinatesModule() {
        testCoordinatesForRectangularDimensions();
        testCoordinatesForSingleRow();
        testCoordinatesForSingleColumn();
        testCoordinatesForZeroDimensions();
        System.out.println("Coordinates module tests passed.");
    }

    private static void testBitPackerModule() {
        String message = "Bit packing round trip";
        BitPacker packer = new BitPacker();
        assertEquals(message, packer.restore(packer.getBits(message)), "BitPacker string round trip");
        System.out.println("BitPacker module tests passed.");
    }

    private static void testCryptoModule() throws Exception {
        String message = "Crypto round trip";
        String password = "test-password";
        CryptoManager crypto = new CryptoManager();
        assertEquals(message, crypto.decrypt(crypto.encrypt(message, password), password),
                "Crypto encryption/decryption round trip");
        System.out.println("Crypto module tests passed.");
    }

    private static void testSteganoModule() {
        String message = "Steganography round trip";
        String password = "test-password";
        BufferedImage image = new BufferedImage(32, 24, BufferedImage.TYPE_INT_ARGB);
        SteganoManager stegano = new SteganoManager();
        BufferedImage encodedImage = stegano.encodeMessage(message, password, image);
        if (encodedImage == null) {
            throw new AssertionError("Steganography encoding unexpectedly failed.");
        }
        assertEquals(message, stegano.decodeMessage(encodedImage, password), "Steganography round trip");
        System.out.println("Stegano module tests passed.");
    }

    private static void testFileManagerModule() throws Exception {
        FileManager fileManager = new FileManager();
        File imageFile = File.createTempFile("vcipher-test-", ".png");
        File messageFile = File.createTempFile("vcipher-test-", ".txt");
        try {
            BufferedImage image = new BufferedImage(4, 3, BufferedImage.TYPE_INT_ARGB);
            image.setRGB(1, 1, 0xFF123456);
            fileManager.writeImage(image, imageFile, "png");
            BufferedImage restoredImage = fileManager.readImageFile(imageFile.getAbsolutePath());
            assertEquals(image.getRGB(1, 1), restoredImage.getRGB(1, 1), "FileManager image round trip");

            String message = "Line one\n\nLine three";
            fileManager.writeMessage(message, messageFile);
            assertEquals(message, FileManager.readMessageFile(messageFile.getAbsolutePath()),
                    "FileManager message round trip");
        } finally {
            Files.deleteIfExists(imageFile.toPath());
            Files.deleteIfExists(messageFile.toPath());
        }
        System.out.println("FileManager module tests passed.");
    }

    private static void testServiceManagerModule() throws Exception {
        ServiceManager serviceManager = new ServiceManager();
        BufferedImage coverImage = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        String message = "Service layer round trip";
        String password = "service-test-password";

        assertEquals(1532L, serviceManager.getMessageCapacityBytes(coverImage), "Service message capacity");
        BufferedImage sealedImage = serviceManager.sealMessage(message, password, coverImage);
        assertEquals(message, serviceManager.revealMessage(sealedImage, password), "Service seal/reveal round trip");
        System.out.println("ServiceManager module tests passed.");
    }

    private static void testThemeConfigModule() {
        ThemeConfig defaultTheme = ThemeConfig.defaultConfig();
        ThemeConfig customTheme = ThemeConfig.builder()
                .background(Color.BLACK)
                .accent(Color.BLUE)
                .fontFamily("Dialog")
                .fontSizeBody(17)
                .headerPadding(1, 2, 3, 4)
                .pagePadding(5, 6, 7, 8)
                .cardRadius(8)
                .build();

        assertColor(Color.BLACK, customTheme.background, "Custom theme background");
        assertColor(Color.BLUE, customTheme.accent, "Custom theme accent");
        assertEquals(17, customTheme.fontSizeBody, "Custom theme body font size");
        assertEquals(8, customTheme.cardRadius, "Custom theme card radius");
        assertEquals("Dialog", customTheme.boldFont(11).getFamily(), "Custom theme font family");
        assertEquals(1, customTheme.headerBorder().getBorderInsets(new JPanel()).top,
                "Custom theme header top padding");
        assertEquals(5, customTheme.pageBorder().getBorderInsets(new JPanel()).top,
                "Custom theme page top padding");
        assertColor(new Color(246, 247, 251), defaultTheme.background, "Default theme background");
        assertEquals(12, defaultTheme.fontSizeBody, "Default theme body font size");
        System.out.println("ThemeConfig module tests passed.");
    }

    private static void testCoordinatesForRectangularDimensions() {
        Coordinates[] coordinates = Coordinates.getCoordinates(3, 2);
        assertEquals(6, coordinates.length, "Rectangular coordinate count");
        assertCoordinate(coordinates[0], 0, 0);
        assertCoordinate(coordinates[1], 1, 0);
        assertCoordinate(coordinates[2], 2, 0);
        assertCoordinate(coordinates[3], 0, 1);
        assertCoordinate(coordinates[4], 1, 1);
        assertCoordinate(coordinates[5], 2, 1);
    }

    private static void testCoordinatesForSingleRow() {
        Coordinates[] coordinates = Coordinates.getCoordinates(3, 1);
        assertEquals(3, coordinates.length, "Single-row coordinate count");
        for (int x = 0; x < coordinates.length; x++) {
            assertCoordinate(coordinates[x], x, 0);
        }
    }

    private static void testCoordinatesForSingleColumn() {
        Coordinates[] coordinates = Coordinates.getCoordinates(1, 3);
        assertEquals(3, coordinates.length, "Single-column coordinate count");
        for (int y = 0; y < coordinates.length; y++) {
            assertCoordinate(coordinates[y], 0, y);
        }
    }

    private static void testCoordinatesForZeroDimensions() {
        assertEquals(0, Coordinates.getCoordinates(0, 3).length, "Zero-width coordinate count");
        assertEquals(0, Coordinates.getCoordinates(3, 0).length, "Zero-height coordinate count");
    }

    private static void testCryptoAndSteganoIntegration() throws Exception {
        String message = "Hello world! I am inevitable";
        String password = "123";
        CryptoManager crypto = new CryptoManager();
        String encrypted = crypto.encrypt(message, password);

        BufferedImage image = new BufferedImage(160, 90, BufferedImage.TYPE_INT_ARGB);
        SteganoManager stegano = new SteganoManager();
        BufferedImage encodedImage = stegano.encodeMessage(encrypted, password, image);
        if (encodedImage == null) {
            throw new AssertionError("Steganography encoding unexpectedly failed.");
        }

        String restored = stegano.decodeMessage(encodedImage, password);
        assertEquals(message, crypto.decrypt(restored, password), "Crypto/steganography round trip");
    }

    private static void assertCoordinate(Coordinates coordinate, int expectedX, int expectedY) {
        assertEquals(expectedX, coordinate.x, "Coordinate x");
        assertEquals(expectedY, coordinate.y, "Coordinate y");
    }

    private static void assertEquals(int expected, int actual, String description) {
        if (expected != actual) {
            throw new AssertionError(description + ": expected " + expected + " but was " + actual);
        }
    }

    private static void assertEquals(long expected, long actual, String description) {
        if (expected != actual) {
            throw new AssertionError(description + ": expected " + expected + " but was " + actual);
        }
    }

    private static void assertColor(Color expected, Color actual, String description) {
        if (!expected.equals(actual)) {
            throw new AssertionError(description + ": expected " + expected + " but was " + actual);
        }
    }

    private static void assertEquals(String expected, String actual, String description) {
        if (!expected.equals(actual)) {
            throw new AssertionError(description + ": expected \"" + expected + "\" but was \"" + actual + "\"");
        }
    }
}