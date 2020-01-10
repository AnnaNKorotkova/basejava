package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        checkNotExistException(resume.getUuid());
        updateInStorage(resume);
        System.out.println("Resume id = \"" + resume.getUuid() + "\" is updated");
    }

    protected abstract boolean isContains(String uuid);

    protected abstract void updateInStorage(Resume resume);

    public void save(Resume resume) {
        checkExistException(resume.getUuid());
        saveToStorage(resume);
        System.out.println("Resume id = \"" + resume.getUuid() + "\" is created");
    }

    protected abstract void saveToStorage(Resume resume);

    public Resume get(String uuid) {
        checkNotExistException(uuid);
        return getFromStorage(uuid);
    }

    protected abstract Resume getFromStorage(String uuid);

    @Override
    public void delete(String uuid) {
        checkNotExistException(uuid);
        deleteInStorage(uuid);
        System.out.println("Resume id = \"" + uuid + "\" is deleted");
    }

    protected abstract void deleteInStorage(String uuid);

    protected abstract Object findElement(String uuid);

    private void checkExistException(String uuid) {
        if (isContains(uuid)) {
            throw new ExistStorageException(uuid);
        }
    }

    private void checkNotExistException(String uuid) {
        if (!isContains(uuid)) {
            throw new NotExistStorageException(uuid);
        }
    }
}