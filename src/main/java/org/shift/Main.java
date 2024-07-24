package org.shift;

import org.shift.enums.StatisticType;
import org.shift.model.SortedSourceData;
import org.shift.utils.OutputFileUtils;
import org.shift.model.InputParams;

import java.io.FileReader;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        try {
            fileWrite(lookingTypeOfNumber(fileRead(readArgsWithReturnInputFilePaths(args))));
        } catch (IllegalArgumentException e) {
            String message = e.getMessage() == null ? "" : e.getMessage();
            System.out.println("Program failed!" + message);
        }
    }

    private static List<String> fileRead(String[] files) { //Метод чтения из входных файлов
        ArrayList<String> writtenText = new ArrayList<>(); //Лист, куда сохраняются все прочитанные строки
        for (int i = 0; i < files.length && (files[i] != null); i++) { //Цикл, итерирующий по каждому файлу
            try (Scanner scan = new Scanner(new FileReader(files[i]))) //Создание сканера, позволяющего считывать строки
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

    private static void fullStatistic(SortedSourceData sortedData) { //Метод подсчета и вывода полной статистики по выходным данным
        // 0 - int
        // 1 - double
        // 2 - string
        //Создание двух Листов для хранения целочисленных данных и чисел с плавающей точкой
        List<Integer> ints = new ArrayList<>();
        for (String s : sortedData.getIntData()) {
            Integer parseInt = Integer.parseInt(s);
            ints.add(parseInt);
        }
        List<Double> doubles = new ArrayList<>();
        for (String s : sortedData.getDoubleData()) {
            Double parseDouble = Double.parseDouble(s);
            doubles.add(parseDouble);
        }
        int maxInt = Integer.MIN_VALUE;
        int minInt = Integer.MAX_VALUE;
        int sumInt = 0;
        for (int number : ints) { //Цикл, необходимый для нахождения наибольшего числа, наименьшего, для подсчета суммы чисел
            if (number > maxInt) {
                maxInt = number;
            }
            if (number < minInt) {
                minInt = number;
            }
            sumInt += number;
        }
        double maxDouble = Double.MIN_VALUE;
        double minDouble = Double.MAX_VALUE;
        double sumDouble = 0;
        for (double number : doubles) { //Цикл, необходимый для нахождения наибольшего числа, наименьшего, для подсчета суммы чисел
            if (number > maxDouble) {
                maxDouble = number;
            }
            if (number < minDouble) {
                minDouble = number;
            }
            sumDouble += number;
        }
        List<String> stringData = sortedData.getStringData();
        int minStringLength = stringData.getFirst().length();
        int maxStringLength = stringData.getFirst().length();
        for (String value : stringData) {
            int length = value.length();
            if (minStringLength > length) {
                minStringLength = length;
            }
            if (maxStringLength < length) {
                maxStringLength = length;
            }
        }
        System.out.println("Full statistic for Integers:\nMaximum: " + maxInt + "\nMinimum: " + minInt + "\nSummary: " + sumInt + "\nAverage: " + String.format("%.3f", (double) (sumInt / ints.size())));
        System.out.println("Full statistic for Doubles:\nMaximum: " + maxDouble + "\nMinimum: " + minDouble + "\nSummary: " + sumDouble + "\nAverage: " + String.format("%.3f", sumDouble / doubles.size()));
        System.out.println("Full statistic for Strings:\nAmount: " + stringData.size() + "\nShortest: " + minStringLength + " symbols\nLongest: " + maxStringLength + " symbols");
    }

    private static SortedSourceData lookingTypeOfNumber(List<String> allValuesFromFiles) { //Метод, разбивающий Лист на двумерный массив, где каждая строка - разный тип данных, а в ячейках хранятся данные
        SortedSourceData sortedData = new SortedSourceData();
        try {

            for (String elem : allValuesFromFiles)
            {
                boolean isFoundMatch=false;
                Pattern pattern = Pattern.compile("^\\d+$");
                Matcher matcher = pattern.matcher(elem);
                while (matcher.find()) {
                    sortedData.addInteger(elem);
                    isFoundMatch=true;
                }
                if (isFoundMatch) continue;
                pattern = Pattern.compile("^\\d+[.,]\\d+[Ee][+-]\\d+$");
                matcher = pattern.matcher(elem);
                while (matcher.find()) {
                    sortedData.addDouble(elem);
                    isFoundMatch=true;
                }
                if (isFoundMatch) continue;
                pattern = Pattern.compile("^\\d+[.,]\\d+$");
                matcher = pattern.matcher(elem);
                while (matcher.find()) {
                    sortedData.addDouble(elem);
                    isFoundMatch=true;
                }
                if (isFoundMatch) continue;
                sortedData.addString(elem);
            }

            if (StatisticType.SHORT.equals(InputParams.getStatisticType())) { //Вывод различных видов статистики
                System.out.println("There are: \n" + sortedData.getIntData().size() + " Integers\n" +
                        sortedData.getDoubleData().size() + " Doubles\n" +
                        sortedData.getStringData().size() + " Strings");
            }
            if (StatisticType.FULL.equals(InputParams.getStatisticType())) {
                fullStatistic(sortedData);
            }

            return sortedData;
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private static void fileWrite(SortedSourceData sortedData) { //Метод записи в выходные файлы
        try {
            OutputFileUtils.createOutputDirectory(InputParams.getFilePath());

            OutputFileUtils.writeOutputFile(InputParams.getFilePath(), InputParams.getFileNamePrefix(),
                    OutputFileUtils.OUTPUT_INTEGER_POSTFIX, InputParams.isAppendable(), sortedData.getIntData());
            OutputFileUtils.writeOutputFile(InputParams.getFilePath(), InputParams.getFileNamePrefix(),
                    OutputFileUtils.OUTPUT_DOUBLE_POSTFIX, InputParams.isAppendable(), sortedData.getDoubleData());
            OutputFileUtils.writeOutputFile(InputParams.getFilePath(), InputParams.getFileNamePrefix(),
                    OutputFileUtils.OUTPUT_STRING_POSTFIX, InputParams.isAppendable(), sortedData.getStringData());
        } catch (NullPointerException e) {
            String message = e.getMessage() == null ? "" : e.getMessage();
            System.out.println("Cant print data in text! NullPointerException" + message);
        }
    }

    private static Path pathFinder() { // Метод поиска пути. В случае, если пользователь введет неверный путь - программа выкинет исключение
        Path path;
        try {
            path = Paths.get(InputParams.getFilePath());
            return path;
        } catch (InvalidPathException e) {
            String message = e.getMessage() == null ? "" : e.getMessage();
            System.out.println("I cant find this path! " + message);
            throw e;
        }
    }

    private static String[] readArgsWithReturnInputFilePaths(String[] args) { //Метод, считывающий аргументы, введенные в консоли
        int j = 0;
        InputParams.getInstance();
        String[] fileName = new String[args.length];
        for (int i = 0; i < args.length; i++) { //Перебор по всему массиву args[]
            if (!args[i].isEmpty()) {
                switch (args[i]) { //Поиск соответствий с конкретными аргументами
                    case "-o"://Задание пути к файлу
                        InputParams.setFilePath(args[i + 1]);
                        try {
                            InputParams.setFilePath(pathFinder().toString());
                        } catch (Exception e) {
                            String message = e.getMessage() == null ? "" : e.getMessage();
                            System.out.println("\n Invalid Path! " + message);
                        }
                        args[i] = null;
                        break;
                    case "-p"://Задание префикса названия файла
                        InputParams.setFileNamePrefix(args[i + 1]);
                        args[i] = null;
                        break;
                    case "-a"://Выбор создания нового файла, либо добавления в старый. Appendable = true - добавление.
                        //Appendable = false - перезапись файла.
                        InputParams.setAppendable(true);
                        args[i] = null;
                        break;
                    case "-s"://Краткая статистика
                        InputParams.setStatisticType(StatisticType.SHORT);
                        args[i] = null;
                        break;
                    case "-f"://Полная статистика
                        InputParams.setStatisticType(StatisticType.FULL);
                        args[i] = null;
                        break;
                }
                if ((args[i] != null) && (args[i].contains(".txt"))) { //Сохранение входных файлов
                    fileName[j++] = args[i];
                }
            }
        }
        return fileName;
    }
}
