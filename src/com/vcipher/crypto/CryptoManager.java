package com.vcipher.crypto;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoManager {

    private static final int T_LEN = 128;
    private static final int SALT_LEN = 16;
    private static final int ITERATIONS = 65536;
    private static final int KEY_LEN = 128;

    private SecretKey getKey(String password, byte[] salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LEN);
        SecretKey secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        return secretKey;
    }

    public String encrypt(String message, String password) throws Exception {
        byte[] messageInBytes = message.getBytes();
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LEN];
        random.nextBytes(salt);
        cipher.init(Cipher.ENCRYPT_MODE, getKey(password, salt));
        byte[] encryptedBytes = cipher.doFinal(messageInBytes);
        return  encode(salt) + ":" + encode(cipher.getIV()) + ":" + encode(encryptedBytes);
    }

    public String decrypt(String encryptedData, String password) throws Exception {
        String salt = encryptedData.split(":")[0], iv = encryptedData.split(":")[1];
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, decode(iv));
        cipher.init(Cipher.DECRYPT_MODE, getKey(password, decode(salt)), spec);
        byte[] decryptedBytes = cipher.doFinal(decode(encryptedData.split(":")[2]));
        return new String(decryptedBytes);
    }

    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data) ;
    }

    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }
}