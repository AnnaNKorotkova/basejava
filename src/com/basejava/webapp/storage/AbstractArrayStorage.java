package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

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
    protected void updateInStorage(Resume resume, Object index) {
        storage[(int) index] = resume;
    }

    @Override
    public void saveToStorage(Resume resume, Object index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("There is not enough space to create a new resume", resume.getUuid());
        } else {
            saveResume(resume, (int) index);
            size++;
        }
    }

    protected abstract void saveResume(Resume resume, Integer index);

    @Override
    protected void deleteInStorage(Object index) {
        deleteResume((int) index);
        storage[size - 1] = null;
        size--;
    }

    public abstract void deleteResume(int index);

    @Override
    public List<Resume> getList() {
        List<Resume> resumes = Arrays.asList(Arrays.copyOf(storage, size));
        return resumes;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected Resume getFromStorage(Object index) {
        return storage[(int) index];
    }

    protected boolean isContains(Object index) {
        return (int) index >= 0;
    }
}