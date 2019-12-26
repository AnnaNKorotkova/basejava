package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume resume) {
        if (size < STORAGE_LIMIT) {
            if (findIndex(resume.getUuid()) == -1) {
                storage[size] = resume;
                size++;
                System.out.println("Resume id = \"" + resume.getUuid() + "\" is created");
            } else {
                System.out.println("Resume id = \"" + resume.getUuid() + "\" is already exist, try to update");
            }
        } else {
            System.out.println("There is not enough space to create a new resume");
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
            System.out.println("Resume id = \"" + uuid + "\" is deleted.");
        } else {
            System.out.println("Resume id = \"" + uuid + "\" is not found");
        }
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