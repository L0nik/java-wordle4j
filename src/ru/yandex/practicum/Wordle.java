package ru.yandex.practicum;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

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
            final WordleDictionaryLoader loader = new WordleDictionaryLoader(log);
            final WordleDictionary dictionary = loader.loadDictionary(pathToDictionary);
            final WordleGame game = new WordleGame(log, dictionary);
            game.play();
        } catch (FileNotFoundException exception) {
            //todo
        } catch (IOException exception) {
            //todo
        }
    }

}
