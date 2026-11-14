package com.davidsir1.trabalhon2.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public final class CriptografiaUtil {
    private static final String CHAVE_SECRETA = System.getenv("CHAVE_SECRETA");
    private static final String ALGORITMO = System.getenv("ALGORITMO_CRIPT");

    private CriptografiaUtil(){}

    public static String criptografar(String texto) {
        try {
            SecretKeySpec encripta = new SecretKeySpec(CHAVE_SECRETA.getBytes(StandardCharsets.UTF_8), ALGORITMO);
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.ENCRYPT_MODE, encripta);
            byte[] criptografado = cipher.doFinal(texto.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(criptografado);
        } catch (NoSuchAlgorithmException
                 | NoSuchPaddingException
                 | IllegalBlockSizeException
                 | BadPaddingException
                 | InvalidKeyException e) {
            System.err.println("Erro ao criptografar texto: " + e.getMessage());
            return e.getMessage();
        }
    }

    public static String descriptografar(String textoCriptografado) {
        try {
            SecretKeySpec decripta = new SecretKeySpec(CHAVE_SECRETA.getBytes(StandardCharsets.UTF_8), ALGORITMO);
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.DECRYPT_MODE, decripta);
            byte[] descriptografado = cipher.doFinal(textoCriptografado.getBytes(StandardCharsets.UTF_8));
            return new String(cipher.doFinal(descriptografado), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException
                 | NoSuchPaddingException
                 | IllegalBlockSizeException
                 | BadPaddingException
                 | InvalidKeyException e) {
            System.err.println("Erro ao descriptografar texto: " + e.getMessage());
            return e.getMessage();
        }
    }
}
