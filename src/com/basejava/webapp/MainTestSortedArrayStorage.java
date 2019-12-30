package com.basejava.webapp;

import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.SortedArrayStorage;
import com.basejava.webapp.storage.Storage;



/**
 * Test for your ArrayStorage implementation
 */
public class MainTestSortedArrayStorage {
    private final static Storage ARRAY_STORAGE = new SortedArrayStorage();
    private static Object myClass;

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
        for (int i = 0; i < 4; i++) {
            ARRAY_STORAGE.save(new Resume());
        }
        Resume r1 = new Resume();
//        Resume r2 = new Resume();
//        Resume r3 = new Resume();
//        Resume r5 = new Resume();
//        Resume r4 = new Resume();
//
//
//        ARRAY_STORAGE.save(r1);
//        ARRAY_STORAGE.save(r2);
//        ARRAY_STORAGE.save(r5);
//        ARRAY_STORAGE.save(r3);
//        ARRAY_STORAGE.update(r1);
//        ARRAY_STORAGE.save(r4);

        printAll();

//        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
//        System.out.println("Size: " + ARRAY_STORAGE.size());
//
////        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
//
//        printAll();
//        ARRAY_STORAGE.delete(r1.getUuid());
//        printAll();
//        ARRAY_STORAGE.clear();
//        printAll();
//
//        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}