package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected abstract void updateInStorage(Resume resume, Object key);

    protected abstract void saveToStorage(Resume resume, Object key);

    protected abstract Resume getFromStorage(Object key);

    protected abstract void deleteInStorage(Object key);

    protected abstract Object findKeyByElement(String uuid);

    protected abstract boolean isContains(Object key);

    protected abstract List<Resume> getList();

    public void update(Resume resume) {
        updateInStorage(resume, checkNotExistException(resume.getUuid()));
        System.out.println("Resume id = \"" + resume.getUuid() + "\" is updated");
    }

    public void save(Resume resume) {
        saveToStorage(resume, checkExistException(resume.getUuid()));
        System.out.println("Resume id = \"" + resume.getUuid() + "\" is created");
    }

    public Resume get(String uuid) {
        return getFromStorage(checkNotExistException(uuid));
    }

    public List<Resume> getAllSorted() {
        List<Resume> storage = getList();
        storage.sort(Resume::compareTo);
        return storage;
    }

    @Override
    public void delete(String uuid) {
        deleteInStorage(checkNotExistException(uuid));
        System.out.println("Resume id = \"" + uuid + "\" is deleted");
    }

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