package com.vcipher.crypto;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoManager {
    public byte[] encrypt(byte[] message, String password) throws Exception {
        try {
            SecureRandom random = new SecureRandom();
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    
            int size = cipher.getBlockSize();
            byte[] salt = new byte[size], iv = new byte[size];
            random.nextBytes(salt);
            random.nextBytes(iv);
    
            cipher.init(Cipher.ENCRYPT_MODE, getKey(password, salt), new IvParameterSpec(iv));
            byte[] encryptedMessage = cipher.doFinal(message);

            byte[] combined = new byte[2*size + encryptedMessage.length]; 
            System.arraycopy(salt, 0, combined, 0, size);
            System.arraycopy(iv, 0, combined, size, size);
            System.arraycopy(encryptedMessage, 0, combined, 2*size, encryptedMessage.length);
            return combined;

        } catch (Exception e) {
            throw e;
        }
    }

    public byte[] decrypt(byte[] encryptedData, String password) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    
            int size = cipher.getBlockSize();
            byte[] salt = Arrays.copyOfRange(encryptedData,0, size);
            byte[] iv = Arrays.copyOfRange(encryptedData, size, 2*size);
            byte[] encryptedMessage = Arrays.copyOfRange(encryptedData, 2*size, encryptedData.length);
    
            cipher.init(Cipher.DECRYPT_MODE, getKey(password, salt), new IvParameterSpec(iv));
            return cipher.doFinal(encryptedMessage);
            
        } catch (Exception e) {
            throw e;
        }
    }

    private SecretKey getKey(String password, byte[] salt) throws Exception {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
            SecretKey secret = factory.generateSecret(spec);
            return new SecretKeySpec(secret.getEncoded(), "AES");
        } catch (Exception e) {
            throw e;
        }
    }
}