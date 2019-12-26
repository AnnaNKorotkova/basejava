package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            System.out.println("There is not enough space to create a new resume");
        } else {
            int index = findIndex(resume.getUuid());
            if (index >= 0) {
                System.out.println("Resume id = \"" + resume.getUuid() + "\" is already exist, try to update");
            } else {
                int newIndex = -(index + 1);
                System.arraycopy(storage, newIndex, storage, newIndex + 1, size - newIndex);
                storage[newIndex] = resume;
                size++;
                System.out.println("Resume id = \"" + resume.getUuid() + "\" is created");
            }
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
            storage[size - 1] = null;
            size--;
            System.out.println("Resume id = \"" + uuid + "\" is deleted");
        } else {
            System.out.println("Resume id = \"" + uuid + "\" is not found");
        }
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
