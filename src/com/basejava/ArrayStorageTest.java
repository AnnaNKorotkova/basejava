package com.basejava;

public class ArrayStorageTest {

    public static void main(String[] args) {

        Resume[] arrResume = new Resume[10000];
        ArrayStorage arrayStorage = new ArrayStorage(arrResume);

        for (int i = 0; i < 3; i++) {
            arrayStorage.save(arrResume);
        }

        arrayStorage.getAll(arrResume);
        arrayStorage.delete(2);

        for (int i = 0; i < 2; i++) {
            arrayStorage.save(arrResume);
        }

        arrayStorage.get(arrResume[2]);
        System.out.println(arrayStorage.getSize());
        arrayStorage.getAll(arrResume);
        arrayStorage.clear(arrResume);
        arrayStorage.getAll(arrResume);
    }
}