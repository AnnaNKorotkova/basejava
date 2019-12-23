package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size - 1, null);
        size = 0;

    }

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index != -1) {
            storage[index] = r;
            System.out.println("Резюме с id = \"" + r.getUuid() + "\" обновлено");
        } else {
            System.out.println("Резюме с id = \"" + r.getUuid() + "\" не найдено");
        }
    }

    public void save(Resume r) {
        if (size < storage.length) {
            if (findIndex(r.getUuid()) == -1) {
                storage[size] = r;
                size++;
                System.out.println("Резюме с id = \"" + r.getUuid() + "\" создано.");
            } else {
                System.out.println("Резюме с id = \"" + r.getUuid() + "\" не найдено");
            }
        } else {
            System.out.println("Не хватает места для записи нового резюме");
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            return storage[index];
        } else {
            System.out.println("Резюме с id = \"" + uuid + "\" не найдено");
            return null;
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
            storage[size - 1] = null;
            size--;
            System.out.println("Резюме с id = \"" + uuid + "\" удалено.");
        } else {
            System.out.println("Резюме с id = \"" + uuid + "\" не найдено");
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

    public int findIndex(String uuid) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                index = i;
            }
        }
        return index;
    }
}