package com.ans.test.datatransformation;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DataTransformationTest {

    public static void main(String[] args) {
        String pdfFile = "anexos/Anexo I.pdf";
        String csvFile = "RolProcedimentos.csv";
        String zipFile = "Teste_SeuNome.zip";

        try {
            // Extrai os dados do PDF
            List<String[]> tableData = extractTableFromPDF(pdfFile);

            // Salvar em CSV
            saveToCSV(tableData, csvFile);

            // Compactar o CSV
            zipFile(csvFile, zipFile);

            System.out.println("Processo concluído com sucesso!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String[]> extractTableFromPDF(String pdfPath) throws IOException {
        List<String[]> data = new ArrayList<>();

        try (PDDocument document = PDDocument.load(new File(pdfPath))) {
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);
            PDFTextStripper tStripper = new PDFTextStripper();

            String pdfFileInText = tStripper.getText(document);
            String[] lines = pdfFileInText.split("\\r?\\n");

            // Processar linhas para extrair dados da tabela
            for (String line : lines) {
                // Implementa lógica para extrair dados da tabela
                // Isso depende da estrutura do PDF
                if (line.matches(".*\\d+\\s+.*")) { // Exemplo simples de filtro
                    String[] row = line.split("\\s{2,}"); // Dividir por múltiplos espaços
                    data.add(row);
                }
            }
        }

        return data;
    }


    private static void saveToCSV(List<String[]> data, String outputFile) throws IOException {
        try (PrintWriter pw = new PrintWriter(new File(outputFile))) {
            // Escrever cabeçalho (substituir OD e AMB)
            pw.println("Código,Descrição,OD (Odontológico),AMB (Ambulatorial)");

            for (String[] row : data) {
                // Substituir abreviações
                String od = row[2].equals("OD") ? "Odontológico" : row[2];
                String amb = row[3].equals("AMB") ? "Ambulatorial" : row[3];

                pw.println(String.join(",",
                        row[0],  // Código
                        "\"" + row[1] + "\"",  // Descrição (entre aspas para lidar com vírgulas)
                        od,
                        amb
                ));
            }
        }
    }


    private static void zipFile(String fileToZip, String zipFileName) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zipFileName);
             ZipOutputStream zipOut = new ZipOutputStream(fos);
             FileInputStream fis = new FileInputStream(fileToZip)) {

            ZipEntry zipEntry = new ZipEntry(fileToZip);
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
        }
    }


}
