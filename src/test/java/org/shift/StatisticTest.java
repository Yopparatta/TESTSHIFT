package org.shift;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shift.model.FullStatistic;
import org.shift.model.SortedSourceData;
import org.shift.service.ArgumentService;
import org.shift.service.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.shift.Main.lookingTypeOfNumber;

public class StatisticTest {
    public final static String EXPECTED_SHORT_STATISTIC = "There are: \n"
            + "11 Integers\n"
            + "5 Doubles\n"
            + "6 Strings";
    public final static String EXPECTED_FULL_STATISTIC = "Full statistic for Integers:\n"
            + "Maximum: 123\n"
            + "Minimum: 1\n"
            + "Summary: 162\n"
            + "Average: 14,727\n"
            + "Full statistic for Doubles:\n"
            + "Maximum: 13.228\n"
            + "Minimum: 1.123123E-23\n"
            + "Summary: 23.57022011\n"
            + "Average: 4,714\n"
            + "Full statistic for Strings:\n"
            + "Amount: 6\n"
            + "Shortest: 1 symbols\n"
            + "Longest: 15 symbols";

    @AfterEach
    @BeforeEach
    void cleanUp() throws IOException {
        Files.deleteIfExists(Paths.get(MainTest.TEST_RESOURCES + "outTest/example-Integer.txt"));
        Files.deleteIfExists(Paths.get(MainTest.TEST_RESOURCES + "outTest/example-Double.txt"));
        Files.deleteIfExists(Paths.get(MainTest.TEST_RESOURCES + "outTest/example-String.txt"));
        Files.deleteIfExists(Paths.get(MainTest.TEST_RESOURCES + "outTest"));
    }

    @Test
    void shortStatisticTest() {
        // Arrange
        String inputFile = Paths.get(MainTest.TEST_RESOURCES + "source/in.txt").toAbsolutePath().toString();
        String inputFile2 = Paths.get(MainTest.TEST_RESOURCES + "source/in2.txt").toAbsolutePath().toString();
        String outputPath = Paths.get(MainTest.TEST_RESOURCES + "outTest/").toAbsolutePath().toString();
        Assertions.assertNotNull(inputFile);

        String[] args = new String[]{
                "-p", "example-",
                "-o", outputPath,
                "-a",
                "-s",
                inputFile, inputFile2
        };
        ArgumentService.parseArgs(args);

        List<String> inputFilePaths = ArgumentService.readInputFilePaths(args);
        List<String> readValues = FileService.readInputFilesValues(inputFilePaths);

        // Act
        SortedSourceData sortedData = lookingTypeOfNumber(readValues);

        // Assert
        Assertions.assertEquals(EXPECTED_SHORT_STATISTIC, sortedData.toShortStatisticString());
    }

    @Test
    void fullStatisticTest() {
        // Arrange
        String inputFile = Paths.get(MainTest.TEST_RESOURCES + "source/in.txt").toAbsolutePath().toString();
        String inputFile2 = Paths.get(MainTest.TEST_RESOURCES + "source/in2.txt").toAbsolutePath().toString();
        String outputPath = Paths.get(MainTest.TEST_RESOURCES + "outTest/").toAbsolutePath().toString();
        Assertions.assertNotNull(inputFile);

        String[] args = new String[]{
                "-p", "example-",
                "-o", outputPath,
                "-a",
                "-f",
                inputFile, inputFile2
        };
        ArgumentService.parseArgs(args);

        List<String> inputFilePaths = ArgumentService.readInputFilePaths(args);
        List<String> readValues = FileService.readInputFilesValues(inputFilePaths);
        SortedSourceData sortedData = lookingTypeOfNumber(readValues);

        FileService.writeResultToOutputFile(sortedData);

        // Act
        FullStatistic fullStatistic = new FullStatistic(sortedData);

        // Assert
        Assertions.assertEquals(EXPECTED_FULL_STATISTIC, fullStatistic.toString());
    }
}
