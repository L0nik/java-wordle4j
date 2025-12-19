package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class WordleGameTest {

    static PrintWriter log;
    WordleDictionary dictionary;
    WordleGame game;

    @BeforeAll
    public static void beforeAll() {
        log = new PrintWriter(System.out);
    }

    @BeforeEach
    public void beforeEach() {
        dictionary = new WordleDictionary();
        String[] words = {"тест1", "тест2", "ответ", "олень", "тест3"};
        for (String word : words) {
            dictionary.addWord(word);
        }
        this.game = new WordleGame(log, dictionary, "ответ");
    }

    @Test
    public void getAnswerTest() {
        assertEquals("ответ", this.game.getAnswer());
    }

    @Test
    public void wordIsAnswerTest() {
        assertTrue(this.game.wordIsAnswer(this.game.getAnswer()));
        assertFalse(this.game.wordIsAnswer("чашка"));
    }

    @Test
    public void validateWordTest() {
        assertDoesNotThrow(() -> this.game.validateWord("тест1"));
    }

    @Test
    public void validateWordExceptionTest() {
        Exception exception = assertThrows(
                WordNotFoundInDictionary.class,
                () -> this.game.validateWord("чашка")
        );
        String expectedMessage = "Слово отсутствует в словаре: чашка";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    public void compareWordToAnswerTest() {
        String result = this.game.compareWordToAnswer("олень");
        String expectedResult = "+-^--";
        assertEquals(expectedResult, result);
    }

    @Test
    public void getHintTest() {
        String result = this.game.compareWordToAnswer("олень");
        String[] absentLetters = {"л", "н", "ь"};
        String[] presentLetters = {"о", "е"};
        Character[] knownLetters = {'о', null, null, null, null};
        String hint = this.game.getHint();
        for (String absentLetter : absentLetters) {
            assertFalse(hint.contains(absentLetter));
        }
        for (String presentLetter : presentLetters) {
            assertTrue(hint.contains(presentLetter));
        }
        for (int i = 0; i < knownLetters.length; i++) {
            if (knownLetters[i] != null) {
                assertEquals(knownLetters[i], hint.charAt(i));
            }
        }
    }

    @Test
    public void getStepsTest() {
        assertEquals(6, this.game.getSteps());
        this.game.compareWordToAnswer("чашка");
        assertEquals(5, this.game.getSteps());
    }
}
