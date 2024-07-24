package org.shift;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

class MainTest {
    public static final String TEST_RESOURCES = "src/test/resources/";

    @AfterEach
    @BeforeEach
    void cleanUp() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_RESOURCES + "outTest/example-Integer.txt"));
        Files.deleteIfExists(Paths.get(TEST_RESOURCES + "outTest/example-Double.txt"));
        Files.deleteIfExists(Paths.get(TEST_RESOURCES + "outTest/example-String.txt"));
        Files.deleteIfExists(Paths.get(TEST_RESOURCES + "outTest"));
    }

    @Test
    void testFirstInputFile() throws IOException {
        // Arrange
        String inputFile = Paths.get(TEST_RESOURCES + "source/in.txt").toAbsolutePath().toString();
        String inputFile2 = Paths.get(TEST_RESOURCES + "source/in2.txt").toAbsolutePath().toString();
        String outputPath = Paths.get(TEST_RESOURCES + "outTest/").toAbsolutePath().toString();
        Assertions.assertNotNull(inputFile);

        String[] args = new String[]{
                "-p", "example-",
                "-o", outputPath,
                "-f",
                inputFile, inputFile2
        };

        // Act
        Main.main(args);

        // Assert
        byte[] actualIntegerFile = Files.readAllBytes(Paths.get(TEST_RESOURCES + "outTest/example-Integer.txt"));
        byte[] expectedIntegerFile = Files.readAllBytes(Paths.get(TEST_RESOURCES + "out/example-Integer.txt"));

        Assertions.assertEquals(new String(actualIntegerFile, StandardCharsets.UTF_8),
                new String(expectedIntegerFile, StandardCharsets.UTF_8),
                "The int files differ!");

        byte[] actualDoubleFile = Files.readAllBytes(Paths.get(TEST_RESOURCES + "outTest/example-Double.txt"));
        byte[] expectedDoubleFile = Files.readAllBytes(Paths.get(TEST_RESOURCES + "out/example-Double.txt"));

        Assertions.assertEquals(new String(actualDoubleFile, StandardCharsets.UTF_8),
                new String(expectedDoubleFile, StandardCharsets.UTF_8),
                "The double files differ!");

        byte[] actualStringFile = Files.readAllBytes(Paths.get(TEST_RESOURCES + "outTest/example-String.txt"));
        byte[] expectedStringFile = Files.readAllBytes(Paths.get(TEST_RESOURCES + "out/example-String.txt"));

        Assertions.assertEquals(new String(actualStringFile, StandardCharsets.UTF_8),
                new String(expectedStringFile, StandardCharsets.UTF_8),
                "The string files differ!");
    }
}
