package com.twitter.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ReaderUtils {

    public static BufferedReader readUsingBufferedReader1(String fileName) {
        try {
            return new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String readUsingBufferedReader(String fileName) throws FileNotFoundException {
        StringBuilder contentBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8));
        bufferedReader.lines().forEach(s -> contentBuilder.append(s).append("\n"));
        return contentBuilder.toString();
    }


    public static String readUsingFile(String fileName) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        // Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8).forEach(s -> contentBuilder.append(s).append("\n"));
        Files.lines(Paths.get(fileName), StandardCharsets.UTF_8).forEach(s -> contentBuilder.append(s).append("\n"));
        return contentBuilder.toString();
    }


    public static Scanner readUsingScanner(String fileName) {
        try {
            return new Scanner(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
