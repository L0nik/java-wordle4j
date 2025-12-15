package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {

    private final PrintWriter log;
    private final int wordLength;

    public WordleDictionaryLoader(PrintWriter log, int wordLength) {
        this.log = log;
        this.wordLength = wordLength;
    }

    public WordleDictionary loadDictionary(String fileName) throws FileNotFoundException, IOException {

        WordleDictionary dictionary = new WordleDictionary(this.log, this.wordLength);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
            while (reader.ready()) {
                String line = reader.readLine();
                if (line.length() == this.wordLength) {
                    dictionary.addWord(line);
                }
            }
        }
        return dictionary;
    }
}
