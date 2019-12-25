package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage implements Storage{
    private static final int STORAGE_LIMIT = 10000;
    private  Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;

    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
            System.out.println("Резюме с id = \"" + resume.getUuid() + "\" обновлено");
        } else {
            System.out.println("Резюме с id = \"" + resume.getUuid() + "\" не найдено");
        }
    }

    public void save(Resume resume) {
        if (size < STORAGE_LIMIT) {
            if (findIndex(resume.getUuid()) == -1) {
                storage[size] = resume;
                size++;
                System.out.println("Резюме с id = \"" + resume.getUuid() + "\" создано.");
            } else {
                System.out.println("Резюме с id = \"" + resume.getUuid() + "\" не найдено");
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

    private int findIndex(String uuid) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                index = i;
            }
        }
        return index;
    }
}