package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        updateInStorage(resume, checkNotExistException(resume.getUuid()));
        System.out.println("Resume id = \"" + resume.getUuid() + "\" is updated");
    }

    protected abstract void updateInStorage(Resume resume, Object key);

    public void save(Resume resume) {
        saveToStorage(resume, checkExistException(resume.getUuid()));
        System.out.println("Resume id = \"" + resume.getUuid() + "\" is created");
    }

    protected abstract void saveToStorage(Resume resume, Object key);

    public Resume get(String uuid) {
        return getFromStorage(checkNotExistException(uuid));
    }

    protected abstract Resume getFromStorage(Object key);

    @Override
    public void delete(String uuid) {
        deleteInStorage(checkNotExistException(uuid));
        System.out.println("Resume id = \"" + uuid + "\" is deleted");
    }

    protected abstract void deleteInStorage(Object key);

    protected abstract Object findKeyByElement(String uuid);

    protected abstract boolean isContains(Object key);

    private Object checkExistException(String uuid) {
        Object key = findKeyByElement(uuid);
        if (isContains(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private Object checkNotExistException(String uuid) {
        Object key = findKeyByElement(uuid);
        if (!isContains(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }
}