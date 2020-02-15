package com.basejava.webapp;

public class DeadlockExample {

    public final static Object one = new Object(), two = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {

            synchronized (one) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (two) {
                    System.out.println("If you read this message you failed the test");
                }
            }
        });

        Thread t2 = new Thread(() -> {

            synchronized (two) {
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (one) {
                    System.out.println("If you read this message you failed the test");
                }
            }
        });
        t1.start();
        t2.start();
    }
}