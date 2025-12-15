package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.Scanner;

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

    private final Scanner scanner;

    public WordleGame(PrintWriter log, WordleDictionary dictionary) {
        this.log = log;
        this.dictionary = dictionary;
        this.scanner = new Scanner(System.in);
    }

    public void play() {
        this.answer = dictionary.getRandomWord();
        this.steps = 6;
        while (true) {
            System.out.printf("Введите слово (осталось %d попыток):\n", steps);
            String userInput = scanner.nextLine();
            String word = userInput.toLowerCase().replaceAll("ё", "е");
            if (word.equals(this.answer)) {
                System.out.println("Это верный ответ, вы выиграли!!!");
                break;
            } else if (word.isBlank()) {
                steps--;
                System.out.println("?????");
            } else {
                steps--;
                System.out.println(checkWord(word));
            }
            if (steps <= 0) {
                System.out.println("У вас закончились попытки, вы проиграли. Правильный ответ: " + this.answer);
                break;
            }
        }
    }

    public String checkWord(String word) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            char charWord = word.charAt(i);
            if (this.answer.charAt(i) == charWord)
                result.append("+");
            else if (this.answer.contains(Character.toString(charWord))) {
                result.append("^");
            } else {
                result.append("-");
            }
        }
        return result.toString();
    }

    public String getHint() {
        return "";
    }

}
