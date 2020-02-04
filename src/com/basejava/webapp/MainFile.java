package com.basejava.webapp;

import java.io.File;

public class MainFile {

    public static void main(String[] args) {

        File dir = new File("./src/com/basejava/webapp");
        findFiles(dir, "");
    }

    public static void findFiles(File dir, String indent) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.isDirectory()) {
                    System.out.println(indent + file.getName());
                } else {
                    System.out.println(indent + file.getName().toUpperCase());
                    findFiles(file, indent + "  ");
                }
            }
        }
    }
}