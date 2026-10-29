# V-Cipher

V-Cipher is a Java desktop application for encrypting a message and hiding it inside an image, then extracting and decrypting it later with the same password. Its interface is built with Swing and uses a configurable squircle-style theme.

## Core engineering architecture

The application is organized into five layers:

1. **Presentation layer — Swing UI**
   - `Screen` provides the Seal and Reveal workflows.
   - It collects images, messages, text-file attachments, and passwords, displays image capacity and previews, and lets the user save results.
   - The UI delegates application operations to `ServiceManager`.
   - `ThemeConfig` supplies a configurable theme. `ThemeConfig.defaultConfig()` provides the current default styling; use `ThemeConfig.builder()` to create a customized instance and pass it to `Screen(ThemeConfig)`.

2. **Service layer — application orchestration**
   - `ServiceManager` is the boundary between the UI and core services.
   - It loads images and messages, reports estimated payload capacity, coordinates encryption and embedding when sealing, coordinates extraction and decryption when revealing, and saves output files.
   - It also reports when the encrypted payload does not fit in the selected image.

3. **Cryptography module**
   - `CryptoManager` uses Java Cryptography Architecture APIs.
   - Messages are encrypted with AES-GCM. A random salt is used to derive the AES key from the password with PBKDF2-HMAC-SHA-256; the salt, GCM IV, and ciphertext are stored with the encrypted payload so they can be used during decryption.

4. **Steganography and scattering engine**
   - `SteganoManager` embeds the encrypted payload in the least-significant bits of the image's red, green, and blue channels, using three bits per pixel.
   - A 32-bit length header precedes the payload. Pixel traversal is shuffled using a password-derived seed, so the same password is required to recover the payload.
   - `BitPacker` handles bit conversion and pixel-channel updates; `Coordinates` supplies image pixel coordinates.

5. **File I/O and persistence**
   - `FileManager` uses `ImageIO` to read supported image formats and write images, and reads and writes message text as UTF-8.
   - The UI saves sealed images as PNG, which preserves the embedded pixel data. Revealed messages can be saved as text files.

### Seal and reveal flow

When sealing (encoding message into the image), `Screen` passes the user's inputs to `ServiceManager`. The service encrypts the message, checks that the encrypted payload fits, embeds it into the image, and returns the resulting image for saving.

When revealing (decoding message from the image), the service extracts the payload using the password-derived traversal order and decrypts it with the supplied password. The same password is required for both operations.

The displayed capacity is an estimate based on available RGB channel bits and the length header. Encryption adds metadata and authentication data, so the actual message that fits can be shorter than the displayed estimate; sealing checks the encoded payload size before writing it.

## Build, run, test, and package

The `program.sh` script requires a JDK. Packaging additionally requires the JDK's `jpackage` tool. Run these commands from the project directory:

```bash
# Compile application classes into bin/
./program.sh -b

# Build if needed and launch the Swing application
./program.sh -r

# Compile the application and run the module/integration tests
./program.sh -t

# Create a platform-specific application image in dist/package/V-Cipher
./program.sh -o 1.0.0
```

The `jpackage` application image is built for the platform on which the command runs. For example, running the script in WSL creates a Linux application image, not a Windows `.exe`.

## Project modules

| Module | Source |
|---|---|
| Application entry point | `src/com/vcipher/Main.java` |
| Swing presentation and theme | `src/com/vcipher/ui/Screen.java`, `src/com/vcipher/ui/ThemeConfig.java` |
| Service orchestration | `src/com/vcipher/io/ServiceManager.java` |
| File I/O | `src/com/vcipher/io/FileManager.java` |
| Cryptography | `src/com/vcipher/crypto/CryptoManager.java` |
| Steganography and bit packing | `src/com/vcipher/stego/SteganoManager.java`, `src/com/vcipher/stego/BitPacker.java` |
| Pixel coordinates | `src/com/vcipher/utils/Coordinates.java` |
| Module and integration tests | `test/com/vcipher/TestRunner.java` |
