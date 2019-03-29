package com.advance.concepts.util.encryption;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

// Java program to calculate SHA hash value
public class AES_De {

    private static final String ALGO = "AES";
    private static final String ENCRYPTION_KEY = "ENCRYPTION_KEY";
    private static String secretKey = "P`19>7H]4nLtr:6$";

    public static String encrypt(String data) throws Exception {
       /*try {
            SecretKeySpec key = generateKey();

            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(data.getBytes());

            byte[] encoded = Base64.encodeBase64(encVal);

            return new String(encoded);
        } catch (Exception e) {

        }
        return "";*/

        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        byte[] encoded = Base64.encodeBase64(encVal);
        return new String(encoded);
    }

    public static String decrypt(String encryptedData) throws Exception {
      /*  try {
            SecretKeySpec key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = Base64.decodeBase64(encryptedData.getBytes());
            byte[] decValue = c.doFinal(decordedValue);
            return new String(decValue);
        } catch (Exception e) {

        }
        return encryptedData;
        */
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = Base64.decodeBase64(encryptedData.getBytes());
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }

    public static void main(String[] args) throws Exception {
        String originalString = "P`19>7H]4nLtr:6$";


        String encryptionString = AES_De.encryption(originalString);
        String decryptionString = AES_De.decryption(encryptionString);

        String encryptedStringOld = AES_De.encrypt(originalString);
        String decryptedStringOld = AES_De.decrypt(encryptedStringOld);

        System.out.println(originalString);

        System.out.println(encryptionString);
        System.out.println(decryptionString);

        System.out.println("encryptedStringOld:" + encryptedStringOld);
        System.out.println("decryptedStringOld:" + decryptedStringOld);


    }

    private static Key generateKey() {
      /*  String systemKeyValue = "TheBestSecretKey";
       // String   systemKeyValue =  System.getProperty("ENCRYPTION_KEY");
        //	 KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
        return new SecretKeySpec(systemKeyValue.getBytes(), ALGO);*/
        String systemKeyValue = "TheBestSecretKey";
        return new SecretKeySpec(systemKeyValue.getBytes(), ALGO);
    }


    public static String encryption(String strToEncrypt) {
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), secretKey.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            return java.util.Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes()));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decryption(String strToDecrypt) {
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), secretKey.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            return new String(cipher.doFinal(java.util.Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

}
