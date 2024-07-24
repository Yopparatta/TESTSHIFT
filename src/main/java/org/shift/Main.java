package org.shift;

import org.shift.model.FullStatistic;
import org.shift.model.SortedSourceData;
import org.shift.service.ArgumentService;
import org.shift.service.FileService;
import org.shift.model.InputParams;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        try {
            ArgumentService.parseArgs(args);

            List<String> inputFilePaths = ArgumentService.readInputFilePaths(args);
            List<String> readValues = FileService.readInputFilesValues(inputFilePaths);
            SortedSourceData sortedData = lookingTypeOfNumber(readValues);

            FileService.writeResultToOutputFile(sortedData);

            switch (InputParams.getInstance().getStatisticType()) {
                case SHORT -> System.out.println(sortedData.toShortStatisticString());
                case FULL -> System.out.println(new FullStatistic(sortedData));
                case null -> {
                }
            }
        } catch (Exception e) {
            String message = e.getMessage() == null ? "" : e.getMessage();
            System.out.println("Program failed!" + message);
        }
    }

    public static SortedSourceData lookingTypeOfNumber(List<String> allValuesFromFiles) { //Метод, разбивающий Лист на двумерный массив, где каждая строка - разный тип данных, а в ячейках хранятся данные
        SortedSourceData sortedData = new SortedSourceData();
        try {
            mainLoop:
            for (String elem : allValuesFromFiles) {
                Pattern pattern = Pattern.compile("^\\d+$");
                Matcher matcher = pattern.matcher(elem);
                while (matcher.find()) {
                    sortedData.addInteger(elem);
                    continue mainLoop;
                }

                pattern = Pattern.compile("^\\d+[.,]\\d+[Ee][+-]\\d+$");
                matcher = pattern.matcher(elem);
                while (matcher.find()) {
                    sortedData.addDouble(elem);
                    continue mainLoop;
                }

                pattern = Pattern.compile("^\\d+[.,]\\d+$");
                matcher = pattern.matcher(elem);
                while (matcher.find()) {
                    sortedData.addDouble(elem);
                    continue mainLoop;
                }

                sortedData.addString(elem);
            }

            return sortedData;
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
