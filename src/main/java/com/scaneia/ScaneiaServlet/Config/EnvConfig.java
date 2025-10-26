package com.scaneia.ScaneiaServlet.Config;

public class EnvConfig {
    public static String getEnv(String chave){
        return System.getenv(chave);
    }
}
