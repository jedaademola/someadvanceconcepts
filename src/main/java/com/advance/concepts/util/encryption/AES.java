package com.advance.concepts.util.encryption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class AES {

    private static final Logger logger = LoggerFactory.getLogger(AES.class);

    private static final String ALGO = "AES";
    private static final String ENCRYPTION_KEY = "TheBestSecretKey";
    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void setKey(String myKey) {
        MessageDigest sha;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, ALGO);
        } catch (NoSuchAlgorithmException e) {
            logger.error("setKey Exception: " + e.getMessage());

        } catch (UnsupportedEncodingException e) {
            logger.error("setKey Exception: " + e.getMessage());

        }
    }

    public static String encrypt(String strToEncrypt, String secret) {
        try {
            setKey(secret);
            //Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            Cipher cipher = Cipher.getInstance(ALGO);//("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {

            logger.error("encrypt Exception: " + e.getMessage());

        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String secret) {
        try {
            setKey(secret);
            //Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            Cipher cipher = Cipher.getInstance(ALGO);//("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            logger.error("decrypt  Exception: " + e.getMessage());

        }
        return null;
    }

    public static void main(String[] args) {

        System.out.println("Encrypted:" + encrypt("P`19>7H]4nLtr:6$", ENCRYPTION_KEY));
        System.out.println("Decrypted:" + decrypt("P`19>7H]4nLtr:6$", ENCRYPTION_KEY));
    }
}
