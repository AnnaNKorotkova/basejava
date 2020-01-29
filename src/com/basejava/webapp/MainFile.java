package com.basejava.webapp;

import java.io.File;
import java.util.Objects;

public class MainFile {

    public static void main(String[] args) {

        File dir = new File("./src/com/basejava/webapp");
        findFiles(dir);
    }

    public static void findFiles(File file) {
        if (file.isDirectory()) {
            File[] listAll = Objects.requireNonNull(file.listFiles());
            for (File f : listAll) {
                System.out.println(f.getPath());
                findFiles(f);
            }
        }
    }
}