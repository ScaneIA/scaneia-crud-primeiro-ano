package com.scaneia.ScaneiaServlet.Config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {
    private final static Dotenv dotenv = Dotenv.load();

    public static String getEnv(String chave){
        return dotenv.get(chave);
    }
}
