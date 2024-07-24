package org.shift.service;

import org.shift.enums.StatisticType;
import org.shift.model.InputParams;

import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.List;

public class ArgumentService {
    public static List<String> readInputFilePaths(String[] args) {
        List<String> filePaths = new ArrayList<>();

        for (String arg : args) {
            if (arg == null || !arg.contains(".txt")) {
                continue;
            }

            filePaths.add(arg);
        }

        return filePaths;
    }

    public static void parseArgs(String[] args) { //Метод, считывающий аргументы, введенные в консоли
        InputParams input = InputParams.getInstance();
        for (int i = 0; i < args.length; i++) { //Перебор по всему массиву args[]
            if (!args[i].isEmpty()) {
                switch (args[i]) { //Поиск соответствий с конкретными аргументами
                    case "-o"://Задание пути к файлу
                        try {
                            parseOutputPathArg(args[i + 1]);
                        } catch (Exception e) {
                            String message = e.getMessage() == null ? "" : e.getMessage();
                            System.out.println("\n Invalid Path! " + message);
                        }
                        args[i] = null;
                        break;
                    case "-p"://Задание префикса названия файла
                        input.setFileNamePrefix(args[i + 1]);
                        args[i] = null;
                        break;
                    case "-a"://Выбор создания нового файла, либо добавления в старый. Appendable = true - добавление.
                        //Appendable = false - перезапись файла.
                        input.setAppendable(true);
                        args[i] = null;
                        break;
                    case "-s"://Краткая статистика
                        input.setStatisticType(StatisticType.SHORT);
                        args[i] = null;
                        break;
                    case "-f"://Полная статистика
                        input.setStatisticType(StatisticType.FULL);
                        args[i] = null;
                        break;
                }
            }
        }
    }

    private static void parseOutputPathArg(String outputPathArg) { // Метод поиска пути. В случае, если пользователь введет неверный путь - программа выкинет исключение
        InputParams input = InputParams.getInstance();
        try {
            input.setFilePath(outputPathArg);
        } catch (InvalidPathException e) {
            String message = e.getMessage() == null ? "" : e.getMessage();
            System.out.println("I cant find this path! " + message);
            throw e;
        }
    }
}
