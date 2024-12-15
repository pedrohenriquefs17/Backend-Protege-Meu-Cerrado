package com.br.protegemeucerrado.ocorrencia.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.web.multipart.MultipartFile;

import com.br.protegemeucerrado.ocorrencia.model.Ocorrencia;

public class UploadUtil {
    
    public static Boolean uploadImagem(MultipartFile imagem){

        boolean success = false;

        if(!imagem.isEmpty()){

            String nomeArquivo = imagem.getOriginalFilename();
            try {
                String pastaUpload = "";
                File dir = new File(pastaUpload);

                if(!dir.exists()){
                    dir.mkdirs();
                }

                //criando arquivo no diretório

                File serverFile = new File(dir.getAbsolutePath() + File.separator + nomeArquivo);
                
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

                stream.write(imagem.getBytes());
                stream.close();

                success = true;

            } catch (Exception e) {
                System.out.println("Falha ao carregar o arquivo:" + nomeArquivo + " =>" + e.getMessage());
            }

        }else{
            System.out.println("Falha, arquivo vazio");
        }

        return success;

    }

}
