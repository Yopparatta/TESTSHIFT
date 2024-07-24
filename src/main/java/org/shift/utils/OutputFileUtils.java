package org.shift.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class OutputFileUtils {
    private static final String OUTPUT_FILE_POSTFIX = ".txt";
    public static final String OUTPUT_INTEGER_POSTFIX = "Integer" + OUTPUT_FILE_POSTFIX;
    public static final String OUTPUT_DOUBLE_POSTFIX = "Double" + OUTPUT_FILE_POSTFIX;
    public static final String OUTPUT_STRING_POSTFIX = "String" + OUTPUT_FILE_POSTFIX;


    public static void writeOutputFile(String outputFolder, String prefix, String filePostfix, boolean appendable,  List<String> outputData) {
        Path ouputFilePath = Paths.get(((outputFolder != null ? outputFolder : ".") + File.separator + prefix + filePostfix));
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
            System.out.println("Data cannot be wrote in file");
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
