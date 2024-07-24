package org.shift.service;

import org.shift.model.InputParams;
import org.shift.model.SortedSourceData;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileService {
    private static final String OUTPUT_FILE_POSTFIX = ".txt";
    public static final String OUTPUT_INTEGER_POSTFIX = "Integer" + OUTPUT_FILE_POSTFIX;
    public static final String OUTPUT_DOUBLE_POSTFIX = "Double" + OUTPUT_FILE_POSTFIX;
    public static final String OUTPUT_STRING_POSTFIX = "String" + OUTPUT_FILE_POSTFIX;

    public static List<String> readInputFilesValues(List<String> filePaths) { //Метод чтения из входных файлов
        ArrayList<String> writtenText = new ArrayList<>(); //Лист, куда сохраняются все прочитанные строки
        for (String path : filePaths) {
            try (Scanner scan = new Scanner(new FileReader(path))) //Создание сканера, позволяющего считывать строки
            {
                while (scan.hasNextLine()) {
                    writtenText.add(scan.nextLine()); //Добавление в Лист считанной строки
                }
            } catch (Exception e) {
                String message = e.getMessage() == null ? "" : e.getMessage();
                System.out.println("Cant read data from import files!" + message);
            }
        }

        return writtenText;
    }

    public static void writeResultToOutputFile(SortedSourceData sortedData) { //Метод записи в выходные файлы
        try {
            FileService.createOutputDirectory(InputParams.getInstance().getFilePath());

            FileService.writeOutputFile(FileService.OUTPUT_INTEGER_POSTFIX, sortedData.getIntData());
            FileService.writeOutputFile(FileService.OUTPUT_DOUBLE_POSTFIX, sortedData.getDoubleData());
            FileService.writeOutputFile(FileService.OUTPUT_STRING_POSTFIX, sortedData.getStringData());
        } catch (NullPointerException e) {
            String message = e.getMessage() == null ? "" : e.getMessage();
            System.out.println("Cant print data in text! NullPointerException" + message);
        }
    }

    private static void writeOutputFile(String filePostfix, List<String> outputData) {
        if (outputData == null || outputData.isEmpty()) {
            return;
        }

        InputParams params = InputParams.getInstance();

        String prefix = params.getFileNamePrefix();
        boolean appendable = params.isAppendable();
        Path ouputFilePath = Paths.get(((params.getFilePath() != null ? params.getFilePath() : ".")
                + File.separator
                + prefix
                + filePostfix));

        if (!Files.exists(ouputFilePath)) {
            try {
                Files.createFile(ouputFilePath);
            } catch (IOException e) {
                String message = e.getMessage() == null ? "" : e.getMessage();
                System.out.println("File cannot be created" + message);
            }
        }

        try (FileWriter writer = new FileWriter(ouputFilePath.toString(), appendable)) { // Создание FileWriter, с атрибутами, согласно введенным при запуске аргументам
            for (String value : outputData) {
                writer.write(value);
                writer.append('\n');
            }
            writer.flush();
        } catch (IOException e) {
            String message = e.getMessage() == null ? "" : e.getMessage();
            System.out.println("Data cannot be wrote in file" + message);
        }
    }

    public static void createOutputDirectory(String filePath) {
        if (filePath != null && !Files.exists(Paths.get(filePath))) {
            try {
                Files.createDirectory(Paths.get(filePath));
            } catch (IOException e) {
                System.out.println("Cannot create a directory");
            }
        }
    }
}
