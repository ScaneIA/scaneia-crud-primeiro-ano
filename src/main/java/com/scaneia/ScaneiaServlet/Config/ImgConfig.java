package com.scaneia.ScaneiaServlet.Config;

import jakarta.servlet.http.Part;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class ImgConfig {
    // transformando a imagem e Bytea para carregar no banco
    public static byte[] transformarBytea(Part arquivo) throws IOException{
        byte[] imagemBytes = null;
        try{
            // pega o arquivo da imagem e verifica se é diferente null
            if(arquivo !=null){
                InputStream fileContent = arquivo.getInputStream();
                // transforma a imagem que está com InputStream em um array de byte
                imagemBytes = IOUtils.toByteArray(fileContent);
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return imagemBytes;
    }

    // transoforma o array de byte em base64 para ser aceito no banco
    public static String transformarBase64(byte[] imagemBytes){
        String imagemBytesString =
                Base64.getEncoder().encodeToString(imagemBytes);
        return imagemBytesString;
    }
}
