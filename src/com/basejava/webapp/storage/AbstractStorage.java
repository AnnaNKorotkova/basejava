package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        updateInStorage(resume, checkNotExistException(resume.getUuid()));
        System.out.println("Resume id = \"" + resume.getUuid() + "\" is updated");
    }

    protected abstract void updateInStorage(Resume resume, Object o);

    public void save(Resume resume) {
        saveToStorage(resume, checkExistException(resume.getUuid()));
        System.out.println("Resume id = \"" + resume.getUuid() + "\" is created");
    }

    protected abstract void saveToStorage(Resume resume, Object o);

    public Resume get(String uuid) {
        return getFromStorage(uuid, checkNotExistException(uuid));
    }

    protected abstract Resume getFromStorage(String uuid, Object o);

    @Override
    public void delete(String uuid) {
        deleteInStorage(uuid, checkNotExistException(uuid));
        System.out.println("Resume id = \"" + uuid + "\" is deleted");
    }

    protected abstract void deleteInStorage(String uuid, Object o);

    protected abstract Object findKeyByElement(String uuid);

    protected abstract boolean isContains(Object o);

    private Object checkExistException(String uuid) {
        Object key = findKeyByElement(uuid);
        if (isContains(uuid)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private Object checkNotExistException(String uuid) {
        Object key = findKeyByElement(uuid);
        if (!isContains(uuid)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }
}