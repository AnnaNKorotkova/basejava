package com.basejava.webapp;

public class DeadlockExample {
    // Два объекта-ресурса
    public final static Object one = new Object(), two = new Object();

    public static void main(String[] args) {

        // Создаем два потока, которые будут
        // конкурировать за доступ к объектам
        // one и two
        Thread t1 = new Thread(() -> {
            // Блокировка первого объекта
            synchronized (one) {
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Блокировка второго объекта
                synchronized (two) {
                    System.out.println("Success!");
                }
            }
        });
        Thread t2 = new Thread(() -> {
            // Блокировка второго объекта
            synchronized (two) {
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Блокировка первого объекта
                synchronized (one) {
                    System.out.println("Success!");
                }
            }
        });

        // Запускаем потоки
        t1.start();
        t2.start();
    }
}
