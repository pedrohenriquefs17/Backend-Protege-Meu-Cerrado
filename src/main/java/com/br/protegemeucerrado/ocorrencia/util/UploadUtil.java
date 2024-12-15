package com.br.protegemeucerrado.ocorrencia.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class UploadUtil {
    
    public static String uploadImagem(MultipartFile imagem){

        if(!imagem.isEmpty()){

            String nomeArquivo = UUID.randomUUID().toString();
            try {
                String pastaUpload = "C:\\Users\\lucas\\Downloads";
                String caminho = pastaUpload+imagem.getOriginalFilename();
                File dir = new File(pastaUpload);

                if(!dir.exists()){
                    dir.mkdirs();
                }

                //criando arquivo no diretório

                File serverFile = new File(dir.getAbsolutePath() + File.separator + nomeArquivo);
                
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

                stream.write(imagem.getBytes());
                stream.close();

                return caminho;

            } catch (Exception e) {
                System.out.println("Falha ao carregar o arquivo:" + nomeArquivo + " =>" + e.getMessage());
            }

        }else{
            System.out.println("Falha, arquivo vazio");
        }

        return null;

    }

}
