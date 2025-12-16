package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.*;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private String answer;
    private int steps;
    private final WordleDictionary dictionary;
    private final PrintWriter log;
    private final List<String> usedWords;
    private final Set<Character> absentLetters;
    private final Set<Character> presentLetters;
    private final Character[] knownLetters;

    public WordleGame(PrintWriter log, WordleDictionary dictionary) {
        this.log = log;
        this.dictionary = dictionary;
        this.usedWords = new ArrayList<>();
        this.absentLetters = new HashSet<>();
        this.presentLetters = new HashSet<>();
        this.knownLetters = new Character[dictionary.getWordLength()];
        this.answer = dictionary.getRandomWord();
        this.steps = 6;
    }

    public boolean wordIsAnswer(String word) {
        return this.answer.equals(word);
    }

    public void validateWord(String word) throws WordNotFoundInDictionary {
        if (!dictionary.containsWord(word)) {
            String message = String.format("Слово отсутствует в словаре: %s", word);
            this.log.println(message);
            throw new WordNotFoundInDictionary(message);
        }
    }

    public String compareWordToAnswer(String word) {
        this.steps--;
        this.usedWords.add(word);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            char charWord = word.charAt(i);
            if (this.answer.charAt(i) == charWord) {
                result.append("+");
                knownLetters[i] = charWord;
            }
            else if (this.answer.contains(Character.toString(charWord))) {
                result.append("^");
                this.presentLetters.add(charWord);
            } else {
                result.append("-");
                this.absentLetters.add(charWord);
            }
        }
        return result.toString();
    }

    public String getHint() {
        if (this.usedWords.isEmpty()) {
            String hint = this.dictionary.getRandomWord();
            while (hint.equals(this.answer)) {
                hint = this.dictionary.getRandomWord();
            }
            return hint;
        }

        for (String word : this.dictionary.getWords()) {
            if (this.usedWords.contains(word)) {
                continue;
            }

            char[] letters = word.toCharArray();
            boolean hasIncorrectLetters = false;
            for (int i = 0; i < letters.length; i++) {
                if (this.knownLetters[i] != null && this.knownLetters[i] != letters[i]) {
                    hasIncorrectLetters = true;
                    break;
                }

                if (this.absentLetters.contains(letters[i])) {
                    hasIncorrectLetters = true;
                    break;
                }
            }
            if (hasIncorrectLetters) {
                continue;
            }

            boolean hasAllCorrectLetters = true;
            for (Character ch : this.presentLetters) {
                if (!word.contains(Character.toString(ch))) {
                    hasAllCorrectLetters = false;
                    break;
                }
            }

            if (hasAllCorrectLetters) {
                return word;
            }

        }
        return "";
    }

    public int getSteps() {
        return this.steps;
    }

    public String getAnswer() {
        return this.answer;
    }

}
