package com.scaneia.ScaneiaServlet.Config;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashSenha {

    // Hash de qualquer senha
    public static String hashSenha(String password) throws NoSuchAlgorithmException {
        MessageDigest algorithm =
                MessageDigest.getInstance("SHA-256");
        byte[] mensagemDigest =
                algorithm.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder hashString = new StringBuilder();
        for (byte b : mensagemDigest) {
            hashString.append(String.format("%02X", 0xFF & b));
        }
        return hashString.toString();
    }

    // Hash fixo da senha
    public static final String HASH_FIXO;
    static {
        String senhaFixa = "Acesso143scaneia";
        String hash = "";
        try {
            hash = hashSenha(senhaFixa);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        HASH_FIXO = hash;
    }
}
