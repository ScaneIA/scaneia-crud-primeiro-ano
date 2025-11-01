package com.scaneia.ScaneiaServlet.Config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {
    private static Dotenv dotenv;

    static{
        dotenv = Dotenv.configure().ignoreIfMissing().load();
    }

    public static String getEnv(String chave){
        if (dotenv == null){
            return System.getenv(chave);
        }
        return dotenv.get(chave);
    }
}
