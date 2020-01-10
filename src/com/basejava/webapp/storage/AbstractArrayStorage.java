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
    protected void updateInStorage(Resume resume) {
        int index = findElement(resume.getUuid());
        storage[index] = resume;
    }

    @Override
    public void saveToStorage(Resume resume) {
        int index = findElement(resume.getUuid());
        if (size == STORAGE_LIMIT) {
            throw new StorageException("There is not enough space to create a new resume", resume.getUuid());
        } else {
            saveResume(resume, index);
            size++;
        }
    }

    protected abstract void saveResume(Resume resume, int index);

    @Override
    protected void deleteInStorage(String uuid) {
        int index = findElement(uuid);
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

    @Override
    protected abstract Integer findElement(String uuid);

    @Override
    protected boolean isContains(String uuid) {
        return findElement(uuid) >= 0;
    }
}