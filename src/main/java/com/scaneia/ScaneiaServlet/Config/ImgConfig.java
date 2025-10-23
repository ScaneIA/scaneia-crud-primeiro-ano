package com.scaneia.ScaneiaServlet.Config;

import jakarta.servlet.http.Part;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class ImgConfig {
    public static byte[] transformarBytea(Part arquivo) throws IOException{
        byte[] imagemBytes = null;
        try{
            if(arquivo !=null){
                InputStream fileContent = arquivo.getInputStream();
                imagemBytes = IOUtils.toByteArray(fileContent);
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return imagemBytes;
    }

    public static String transformarBase64(byte[] imagemBytes){
        String imagemBytesString =
                Base64.getEncoder().encodeToString(imagemBytes);
        return imagemBytesString;
    }
}
