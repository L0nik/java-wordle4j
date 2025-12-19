package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryTest {

    static PrintWriter log;
    WordleDictionary dictionary;

    @BeforeAll
    public static void beforeAll() {
        log = new PrintWriter(System.out);
    }

    @BeforeEach
    public void beforeEach() {
        dictionary = new WordleDictionary();
    }

    @Test
    public void addWordTest() {
        dictionary.addWord("ЁжИк");
        assertEquals("ёжик", dictionary.getRandomWord());
    }

    @Test
    public void getRandomWordTest() {
        String[] words = {"тест1", "тест2", "тест3"};
        for (String word : words) {
            dictionary.addWord(word);
        }
        String randomWord = dictionary.getRandomWord();
        boolean randomWordIsCorrect = false;
        for (String word : words) {
            if (word.equals(randomWord)){
                randomWordIsCorrect = true;
                break;
            }
        }
        assertTrue(randomWordIsCorrect);
    }

    @Test
    public void getWordsTest() {
        String[] words = {"тест1", "тест2", "тест3"};
        for (String word : words) {
            dictionary.addWord(word);
        }
        List<String> dictionaryWords = dictionary.getWords();
        assertEquals(3, dictionaryWords.size());
        for (int i = 0; i < words.length; i++) {
            assertEquals(words[i], dictionaryWords.get(i));
        }
    }

    @Test
    public void containsWordTest() {
        dictionary.addWord("тест");
        assertTrue(dictionary.containsWord("тест"));
        assertFalse(dictionary.containsWord("test"));
    }

    @Test
    public void sizeTest() {
        dictionary.addWord("тест");
        assertEquals(1, dictionary.size());
    }
}
