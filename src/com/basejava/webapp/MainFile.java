package com.basejava.webapp;

import java.io.File;
import java.util.Objects;

public class MainFile {

    public static void main(String[] args) {

        File dir = new File("./src/com/basejava/webapp");
        findFiles(dir);
    }
    static int count = -1;
    public static void findFiles(File file) {
        if (file.isDirectory()) {
            count++;
            File[] listAll = Objects.requireNonNull(file.listFiles());
            for (File f : listAll) {
                System.out.println("    ".repeat(count) + f.getName());
                findFiles(f);
            }
            count = 0;
        }
    }
}