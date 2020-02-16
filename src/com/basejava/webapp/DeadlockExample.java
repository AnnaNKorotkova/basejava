package com.basejava.webapp;

public class DeadlockExample {

    public final static Object one = new Object(), two = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> sync(one, two));
        Thread t2 = new Thread(() -> sync(two, one));
        t1.start();
        t2.start();
    }

    private static void sync(Object one, Object two) {
        synchronized (two) {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (one) {
                System.out.println("If you read this message you failed the test");
            }
        }
    }
}