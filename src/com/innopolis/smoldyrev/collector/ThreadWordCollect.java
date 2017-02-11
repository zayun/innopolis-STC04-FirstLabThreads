package com.innopolis.smoldyrev.collector;

import java.util.ArrayList;

/**
 * Created by smoldyrev on 09.02.17.
 * Отправляет Массив строк в счетчик
 */
public class ThreadWordCollect implements Runnable {

    private ArrayList<String> words;

    private WordCollector collector;

    private volatile static boolean error = false;

    public ThreadWordCollect(ArrayList<String> words, WordCollector collector) {
        this.words = words;
        this.collector = collector;
    }

    public static void setError(boolean error) {
        ThreadWordCollect.error = error;
    }

    @Override
    public void run() {

        for (String str :
                words) {
            if (!error) {
                collector.put(str);
            } else {
                return;
            }
        }
    }

}
