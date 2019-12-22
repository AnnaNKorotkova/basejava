package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

import static java.lang.Integer.parseInt;

public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
            size = 0;
        }
    }

    public void update(Resume r) {
        if (findIndexByUuid(r.getUuid()) != null) {
            storage[parseInt(findIndexByUuid(r.getUuid()))] = r;
            System.out.println("Резюме с id = " + r.getUuid() + " изменено.");
        } else {
            System.out.println("Резюме с таким id: " + r.getUuid() + " не найдено");
        }
    }

    public void save(Resume r) {
        if (size < storage.length) {
            if (findIndexByUuid(r.getUuid()) == null) {
                storage[size] = r;
                size++;
                System.out.println("Резюме с id = " + r.getUuid() + " создано.");
            } else {
                System.out.println("Резюме с таким id: " + r.getUuid() + " не найдено");
            }
        } else {
            System.out.println("Не хватает места для записи нового резюме");
        }

    }

    public Resume get(String uuid) {
        if (findIndexByUuid(uuid) != null) {
            return storage[parseInt(findIndexByUuid(uuid))];
        } else {
            System.out.println("Резюме с таким id: " + uuid + " не найдено");
            return null;
        }
    }

    public void delete(String uuid) {
        if (findIndexByUuid(uuid) != null) {
            int index = parseInt(findIndexByUuid(uuid));
            for (int j = index; j < size - 1; j++) {
                storage[j] = storage[j + 1];
            }
            storage[size - 1] = null;
            size--;
            System.out.println("Резюме с id = " + uuid + " удалено.");
        } else {
            System.out.println("Резюме с таким id: " + uuid + " не найдено");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public String findIndexByUuid(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return String.valueOf(i);
            }
        }
        return null;
    }
}