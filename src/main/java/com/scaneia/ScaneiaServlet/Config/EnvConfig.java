package com.scaneia.ScaneiaServlet.Config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {
    private static Dotenv dotenv;

    static{
        dotenv = Dotenv.configure().ignoreIfMissing().load();
    }

    public static String getEnv(String chave){
        if (dotenv == null){
            System.out.println("variavel do sistema usada");
            return System.getenv(chave);
        }
        System.out.println("variavel dotenv usada");
        return dotenv.get(chave);
    }
}
