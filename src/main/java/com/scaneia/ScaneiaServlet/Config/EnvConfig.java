package com.scaneia.ScaneiaServlet.Config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {
    private static Dotenv dotenv;

    static{
        dotenv = Dotenv.configure().ignoreIfMissing().load();
    }

    public static String getEnv(String chave){
        //pega a variavel pelo dotenv
        String env = dotenv.get(chave);
        if (env == null){
            //retorna a variavel de ambiente
            return System.getenv(chave);
        }

        //retorna a variavel do dotenv
        return env;
    }
}
