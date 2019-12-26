package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    public void saveResume(Resume resume, int index) {
        storage[size] = resume;
        size++;
    }

    public void deleteResume(String uuid, int index) {
            storage[index] = storage[size - 1];
     }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    protected int findIndex(String uuid) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                index = i;
            }
        }
        return index;
    }
}