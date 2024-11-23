package com.questly.questly_backend.auth;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class DecryptUtility {
    public static void writeIV() throws Exception{
        byte[] iv = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        FileOutputStream fs = new FileOutputStream(new File("src/main/resources/static/Keys/IV.txt"));
        BufferedOutputStream bos = new BufferedOutputStream(fs);
        bos.write(iv);
        bos.close();
    }

    public static byte[] readIV() throws Exception{
        byte[] fileData = new byte[16];
        DataInputStream dis = null;

        dis = new DataInputStream(new FileInputStream(new File("src/main/resources/static/Keys/IV.txt")));
        dis.readFully(fileData);
        if (dis != null) {
            dis.close();
        }
        return fileData;
    }

    public static String decrypt(String algorithm, String cipherText, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        //new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static String encrypt(String algorithm, String input, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }


}
