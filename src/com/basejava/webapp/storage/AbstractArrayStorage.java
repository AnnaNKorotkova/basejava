package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
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
    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            System.out.println("Resume id = \"" + resume.getUuid() + "\" is updated");
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            if (size == STORAGE_LIMIT) {
                throw new StorageException("There is not enough space to create a new resume", resume.getUuid());
            } else {
                saveResume(resume, index);
                size++;
                System.out.println("Resume id = \"" + resume.getUuid() + "\" is created");
            }
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    protected abstract void saveResume(Resume resume, int index);

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            deleteResume(index);
            storage[size - 1] = null;
            size--;
            System.out.println("Resume id = \"" + uuid + "\" is deleted");
        } else {
            throw new NotExistStorageException(uuid);
        }
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

    protected abstract int findIndex(String uuid);
}