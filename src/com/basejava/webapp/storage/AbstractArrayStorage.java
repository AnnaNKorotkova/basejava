package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    protected abstract void saveResume(Resume resume, Integer index);

    public abstract void deleteResume(int index);

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void updateInStorage(Resume resume, Integer index) {
        storage[index] = resume;
    }

    @Override
    public void saveToStorage(Resume resume, Integer index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("There is not enough space to create a new resume", resume.getUuid());
        } else {
            saveResume(resume, index);
            size++;
        }
    }

    @Override
    protected void deleteInStorage(Integer index) {
        deleteResume(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public List<Resume> getList() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected Resume getFromStorage(Integer index) {
        return storage[index];
    }

    protected boolean isContains(Integer index) {
        return index >= 0;
    }
}