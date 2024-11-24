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


}
