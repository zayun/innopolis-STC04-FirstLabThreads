package com.innopolis.smoldyrev.resource;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class ParsedTextFile {

    private final String filePath;

    private ArrayList<String> words = new ArrayList<>(/*file.length()/16?*/);

    public ArrayList<String> getWords() {
        return words;
    }

    public String getFilePath() {
        return filePath;
    }

    /**
     * Конструктор - создание нового объекта
     * Разбивает полученный файл на слова
     * и добавляет их в массив words
     * @see ParsedTextFile#ParsedTextFile(String)
     */
    public ParsedTextFile(String filePath) throws IOException {

        this.filePath = filePath;
        InputStream stream;

        if (filePath.contains("http://")||filePath.contains("https://")) {
            URL myURL = new URL(filePath);
            stream = myURL.openStream();
        } else {
            stream = new FileInputStream(filePath);
        }

        BufferedReader buffReader = new BufferedReader(new InputStreamReader(stream));
        parseReader(buffReader);
    }

    /**
     * <p>Разделяет текст на слова и сохраняет их в words</p>
     * перед добавлением удаляет незначимые символы (знаки препинания(кроме "-") и цифры)
     * и переводит строку в нижний регистр
     * @throws IllegalArgumentException если в файле встречается неразрешенный символ
     * @throws IOException если есть проблемы с чтением файла
     */
    private void parseReader(BufferedReader buffReader) throws IOException {
        while (buffReader.ready()) {

            for (String str: buffReader.readLine().split("\\s+")) {

                if (isValidValue(str)) {
                    /*приводим слова в правильный вид*/
                    str = str.replaceAll("[^А-Яа-яёЁ-]","").toLowerCase();

                    if (!("".equals(str))) {
                        words.add(str);
                    }

                } else {
                    throw new IllegalArgumentException("Текст \""+ getFilePath() + "\" содержит не кирилические символы!");
                }
            }
        }
    }

    /**
     * <p>Проверяет, является ли строка допустимой</p>
     * Строка допустима, если в ней содержатся только:
     * символы кириллицы
     * цифры
     * знаки препинания
     * @param str Проверяемая строка
     * @return true, если входная строка допустима, и false, если недопустима
     */
    private boolean isValidValue(String str){

        final String VALID_SYMBOLS = "[А-Яа-яёЁ\\d\\s\\\\/_,.\\-—?!№%\":*();`]*";

        return str.matches(VALID_SYMBOLS);
    }
}
