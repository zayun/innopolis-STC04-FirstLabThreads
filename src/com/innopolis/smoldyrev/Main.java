package com.innopolis.smoldyrev;

import com.innopolis.smoldyrev.collector.ThreadWordCollect;
import com.innopolis.smoldyrev.collector.WordCollector;
import com.innopolis.smoldyrev.resource.ParsedTextFile;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        WordCollector wordCollector = new WordCollector();
        for (String filePath :
                args) {
            try {

                Thread thread = new Thread(
                        new ThreadWordCollect(
                                new ParsedTextFile(filePath).getWords(), wordCollector));
                thread.start();

            } catch (IOException e) {
                ThreadWordCollect.setError(true);
                System.out.println(e.getMessage());
            }
        }

    }
}
