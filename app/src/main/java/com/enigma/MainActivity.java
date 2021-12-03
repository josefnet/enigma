package com.enigma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {


    KeyGenerator keyGenerator;
    SecretKey secretKey;

    byte[] IV = new byte[16];
    SecureRandom random;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        random = new SecureRandom();
        random.nextBytes(IV);

        try {
            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            secretKey = keyGenerator.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }



        try {
            String txt = "https://www.google.com";
            String encryptText = new String(encrypt(txt.getBytes(), secretKey, IV), "UTF-8");
            String decrypt = decrypt(encrypt(txt.getBytes(), secretKey, IV), secretKey, IV);
            Log.v("&&_crypted",encryptText);
            Log.v("&&_decrypted",decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(getBaseContext(), "END Application", Toast.LENGTH_SHORT).show();

    }

    public static byte[] encrypt(byte[] plaintext, SecretKey key, byte[] IV) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] cipherText = cipher.doFinal(plaintext);
        return cipherText;
    }


    public static String decrypt(byte[] cipherText, SecretKey key, byte[] IV) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(IV);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] decryptedText = cipher.doFinal(cipherText);
            return new String(decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}