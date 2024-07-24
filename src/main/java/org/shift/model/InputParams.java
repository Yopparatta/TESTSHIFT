package org.shift.model;

import org.shift.enums.StatisticType;

public class InputParams {
    private static boolean appendable = false; //Переменная указывает на наличие аргумента -a
    private static String fileNamePrefix; //Переменная указывающая префикс имени файла
    private static String filePath; //Переменная указывающая на путь к файлу
    private static StatisticType statisticType; // Переменная указывающая на аргумент -s или -f, краткая и полная статистика

    private static InputParams instance;
    private InputParams(){}
    public static InputParams getInstance(){
        if (instance == null) {
            instance = new InputParams();
        }
        return instance;
    }

    public static boolean isAppendable() {
        return appendable;
    }

    public static void setAppendable(boolean appendable) {
        InputParams.appendable = appendable;
    }

    public static String getFileNamePrefix() {
        return fileNamePrefix;
    }

    public static void setFileNamePrefix(String fileNamePrefix) {
        InputParams.fileNamePrefix = fileNamePrefix;
    }

    public static String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String filePath) {
        InputParams.filePath = filePath;
    }

    public static StatisticType getStatisticType() {
        return statisticType;
    }

    public static void setStatisticType(StatisticType statisticType) {
        InputParams.statisticType = statisticType;
    }
}
