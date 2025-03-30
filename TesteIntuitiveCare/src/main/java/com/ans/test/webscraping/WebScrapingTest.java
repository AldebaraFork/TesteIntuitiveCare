package com.ans.test.webscraping;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.File;

public class WebScrapingTest {
    public static void main(String[] args){
        String url = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";
        String outputFolder = "anexos/";
        String zipFileName = "anexos.zip";

        try{
            //Criar diretorio se nao existe um
            new File(outputFolder).mkdirs();

            //Conectar no site e obtem o HTML
            Document doc = Jsoup.connect(url).get();

            //Encontra todos os links para PDF (Anexo I e II)
            Elements links = doc.select("a[href$=.pdf]");
            List<String> pdfFiles = new ArrayList<>();

            for (Element link : links) {
                String pdfUrl = link.attr("href");
                String fileName = pdfUrl.substring(pdfUrl.lastIndexOf('/') + 1);

                // Baixar apenas Anexo I e II
                if (fileName.toLowerCase().contains("anexo i") || fileName.toLowerCase().contains("anexo ii")) {
                    System.out.println("Baixando: " + fileName);
                    downloadFile(pdfUrl, outputFolder + fileName);
                    pdfFiles.add(outputFolder + fileName);
                }

            }
            // Compactar os arquivos
            if (!pdfFiles.isEmpty()) {
                System.out.println("Compactando arquivos...");
                zipFiles(pdfFiles, zipFileName);
                System.out.println("Arquivos compactados em: " + zipFileName);
            } else {
                System.out.println("Nenhum arquivo PDF encontrado para download.");
            }

        }catch (IOException e) {
        e.printStackTrace();
    }
    }
    private static void downloadFile(String fileUrl, String outputFileName) throws IOException {
        URL url = new URL(fileUrl);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(outputFileName);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }
    private static void zipFiles(List<String> files, String zipFileName) throws IOException {
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFileName))) {
            for (String filePath : files) {
                File file = new File(filePath);
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zipOut.putNextEntry(zipEntry);

                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                }
            }
        }
    }
}
