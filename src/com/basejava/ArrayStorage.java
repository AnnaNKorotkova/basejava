package com.basejava;

import java.util.Arrays;

public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, null);

    }

    void save(Resume r) {
        storage[size()] = r;
        System.out.println("Резюме с id = " + r.uuid + " создано.");
    }

    Resume get(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (uuid.equals(storage[i].uuid)) {
                return storage[i];
            }
        }
        System.out.println("Невозможно найти резюме с id = " + uuid + ", такого резюме не существует.");
        return null;
    }

    void delete(String uuid) {
        int index = 0;
        for (int i = 0; i < size(); i++) {
            if (uuid.equals(storage[i].uuid)) {
                index = i;
                for (int k = index; k < size() - 1; k++) {
                    storage[k] = storage[k + 1];
                }
                storage[size() - 1] = null;
                System.out.println("Резюме с id = " + uuid + " удалено.");
                break;
            } else {
                System.out.println("Невозможно удалить резюме с id = " + uuid + ", такого резюме не существует.");
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size());
    }

    int size() {
        int count = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                count++;
            }
        }
        return count;
    }
}