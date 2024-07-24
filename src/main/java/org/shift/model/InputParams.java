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

    public boolean isAppendable() {
        return appendable;
    }

    public void setAppendable(boolean appendable) {
        InputParams.appendable = appendable;
    }

    public String getFileNamePrefix() {
        return fileNamePrefix;
    }

    public void setFileNamePrefix(String fileNamePrefix) {
        InputParams.fileNamePrefix = fileNamePrefix;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        InputParams.filePath = filePath;
    }

    public StatisticType getStatisticType() {
        return statisticType;
    }

    public void setStatisticType(StatisticType statisticType) {
        InputParams.statisticType = statisticType;
    }
}
