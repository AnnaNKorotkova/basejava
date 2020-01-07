package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void updateStorageElement(int index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    public void saveStorageElement(Resume resume, int index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("There is not enough space to create a new resume", resume.getUuid());
        } else {
            saveResume(resume, index);
            size++;
            System.out.println("Resume id = \"" + resume.getUuid() + "\" is created");
        }
    }

    protected abstract void saveResume(Resume resume, int index);

    @Override
    public Resume getStorageElement(int index) {
        return storage[index];
    }

    @Override
    public void removeStorageElement(int index) {
        deleteResume(index);
        storage[size - 1] = null;
        size--;
    }

    public abstract void deleteResume(int index);

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }
}