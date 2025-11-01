package com.scaneia.ScaneiaServlet.Config;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashSenha {

    // Hash de qualquer senha, para manda-lá de forma "criptografada" para o banco
    public static String hashSenha(String password) throws NoSuchAlgorithmException {
        MessageDigest algorithm =
                // usamos a biblioteca SHA-256 que transforma a String para um String de 64 dígitos
                MessageDigest.getInstance(EnvConfig.getEnv("ALGORITIMO_HASH"));
        // transforma a senha recebida em bytes
        byte[] mensagemDigest =
                algorithm.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder hashString = new StringBuilder();
        for (byte b : mensagemDigest) {
            hashString.append(String.format("%02X", 0xFF & b));
        }
        // retorna a senha de forma criptografada
        return hashString.toString();
    }
}
