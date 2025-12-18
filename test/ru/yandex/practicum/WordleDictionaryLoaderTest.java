package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class WordleDictionaryLoaderTest {

    static PrintWriter log;

    @BeforeAll
    public static void beforeAll() {
        log = new PrintWriter(System.out);
    }

    @Test
    public void loadDictionaryTest() {
        String[] words = {"тест1", "тест_много_букв", "тест2", "тест3"};
        String filename = "dictionary_test.txt";
        int wordLength = 5;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String word : words) {
                writer.write(word + "\n");
            }
        } catch (IOException exception) {
            exception.printStackTrace(log);
        }

        WordleDictionaryLoader dictionaryLoader = new WordleDictionaryLoader(log, wordLength);
        WordleDictionary dictionary = dictionaryLoader.loadDictionary(filename);
        assertEquals(3, dictionary.size());
        assertFalse(dictionary.containsWord("тест_много_букв"));

        try {
            Files.deleteIfExists(Paths.get(filename));
        } catch (IOException exception) {
            exception.printStackTrace(log);
        }
    }
}
