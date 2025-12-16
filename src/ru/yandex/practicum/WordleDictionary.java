package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private final List<String> words;
    private final PrintWriter log;
    private final int wordLength;

    public WordleDictionary(PrintWriter log, int wordLength) {
        this.words = new ArrayList<String>();
        this.log = log;
        this.wordLength = wordLength;
    }

    public int getWordLength() {
        return this.wordLength;
    }

    public void addWord(String word) {
        String preparedWord = word.toLowerCase().replaceAll("ё", "е");
        words.add(preparedWord);
    }

    public String getRandomWord() {
        Random random = new Random();
        int index = random.nextInt(0, words.size());
        return words.get(index);
    }

    public List<String> getWords() {
        return this.words;
    }

    public boolean containsWord(String word) {
        return this.words.contains(word);
    }

    public boolean isEmpty() {
        return this.words.isEmpty();
    }

}
