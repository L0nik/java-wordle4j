package ru.yandex.practicum;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static void main(String[] args) {
        try (final PrintWriter log = new PrintWriter(new FileOutputStream("log.txt"), false, StandardCharsets.UTF_8)) {
            final String pathToDictionary = "words_ru.txt";
            final WordleDictionaryLoader loader = new WordleDictionaryLoader(log, 5);
            final WordleDictionary dictionary = loader.loadDictionary(pathToDictionary);
            final WordleGame game = new WordleGame(log, dictionary);
            play(game);
        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void play(WordleGame game) {
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.printf("Введите слово (осталось %d попыток):\n", game.getSteps());
            String userInput = scanner.nextLine();
            String word = userInput.toLowerCase().replaceAll("ё", "е");
            if (word.isBlank()) {
                word = game.getHint();
                System.out.println(word);
            }
            if (game.wordIsAnswer(word)) {
                System.out.println("Это верный ответ, вы выиграли!!!");
                break;
            }
            try {
                game.validateWord(word);
            } catch (WordNotFoundInDictionary exception) {
                System.out.println(exception.getMessage());
                continue;
            }
            System.out.println(game.compareWordToAnswer(word));
            if (game.getSteps() <= 0) {
                System.out.println("У вас закончились попытки, вы проиграли. Правильный ответ: " + game.getAnswer());
                break;
            }
        }
    }

}
