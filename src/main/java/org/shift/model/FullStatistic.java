package org.shift.model;

import java.util.ArrayList;
import java.util.List;

public class FullStatistic {
    private int maxInt;
    private int minInt;
    private long sumInt;
    private double avgInt;

    private double maxDouble;
    private double minDouble;
    private double sumDouble;
    private double avgDouble;

    private int amountString;
    private int maxStringLength;
    private int minStringLength;

    public FullStatistic(SortedSourceData sortedData) {
        List<Integer> ints = new ArrayList<>();
        for (String s : sortedData.getIntData()) {
            Integer parseInt = Integer.parseInt(s);
            ints.add(parseInt);
        }

        this.maxInt = Integer.MIN_VALUE;
        this.minInt = Integer.MAX_VALUE;
        this.sumInt = 0L;
        for (int number : ints) { //Цикл, необходимый для нахождения наибольшего числа, наименьшего, для подсчета суммы чисел
            if (number > maxInt) {
                maxInt = number;
            }
            if (number < minInt) {
                minInt = number;
            }
            sumInt += number;
        }

        if (!ints.isEmpty()) {
            this.avgInt = (double) this.sumInt / ints.size();
        }

        List<Double> doubles = new ArrayList<>();
        for (String s : sortedData.getDoubleData()) {
            Double parseDouble = Double.parseDouble(s);
            doubles.add(parseDouble);
        }

        this.maxDouble = Double.MIN_VALUE;
        this.minDouble = Double.MAX_VALUE;
        this.sumDouble = 0;
        for (double number : doubles) { //Цикл, необходимый для нахождения наибольшего числа, наименьшего, для подсчета суммы чисел
            if (number > maxDouble) {
                maxDouble = number;
            }
            if (number < minDouble) {
                minDouble = number;
            }
            sumDouble += number;
        }

        if (!doubles.isEmpty()) {
            this.avgDouble = this.sumDouble / doubles.size();
        }

        List<String> stringData = sortedData.getStringData();
        this.amountString = stringData.size();
        this.minStringLength = stringData.getFirst().length();
        this.maxStringLength = stringData.getFirst().length();
        for (String value : stringData) {
            int length = value.length();
            if (this.minStringLength > length) {
                this.minStringLength = length;
            }
            if (this.maxStringLength < length) {
                this.maxStringLength = length;
            }
        }
    }

    @Override
    public String toString() {
        return "Full statistic for Integers:"
                + "\nMaximum: " + maxInt
                + "\nMinimum: " + minInt
                + "\nSummary: " + sumInt
                + "\nAverage: " + String.format("%.3f", avgInt) + "\n"

                + "Full statistic for Doubles:"
                + "\nMaximum: " + maxDouble
                + "\nMinimum: " + minDouble
                + "\nSummary: " + sumDouble
                + "\nAverage: " + String.format("%.3f", avgDouble) + "\n"

                + "Full statistic for Strings:"
                + "\nAmount: " + amountString
                + "\nShortest: " + minStringLength + " symbols"
                + "\nLongest: " + maxStringLength + " symbols";
    }
}
