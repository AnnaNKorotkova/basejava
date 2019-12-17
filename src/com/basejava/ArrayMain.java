package com.basejava;

public class ArrayMain {

    public static void main(String[] args) {

        Resume[] res = new Resume[10000];
        for (int i = 0; i < 10000 - 1; i++) {
            res[i] = new Resume();
        }
        for (int i = 0; i < 3; i++) {
            res[i].enterResume();
            res[i].setUuid(i+1);
        }
        for (int i = 0; i < 3; i++) {
            System.out.println(res[i].toString());
        }
    }
}




