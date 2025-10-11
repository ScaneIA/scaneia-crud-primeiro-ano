package com.scaneia.ScaneiaServlet.Config;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashSenha {
    public static String hashSenha(String password) throws NoSuchAlgorithmException {
        MessageDigest algorithm =
                MessageDigest.getInstance(EnvConfig.getEnv("ALGORITIMO_HASH"));
        byte[] mensagemDigest =
                algorithm.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder hashString = new StringBuilder();
        for (byte b : mensagemDigest) {
            hashString.append(String.format("%02X", 0xFF & b));
        }
        return hashString.toString();
    }
}
