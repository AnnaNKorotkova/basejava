package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            updateStorageElement(index, resume, resume.getUuid());
            System.out.println("Resume id = \"" + resume.getUuid() + "\" is updated");
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public abstract void updateStorageElement(int index, Resume resume, String uuid);

    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            saveStorageElement(resume, index);
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    public abstract void saveStorageElement(Resume resume, int index);

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return getStorageElement(index, uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public abstract Resume getStorageElement(int index, String uuid);

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            removeStorageElement(index, uuid);
            System.out.println("Resume id = \"" + uuid + "\" is deleted");
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public abstract void removeStorageElement(int index, String uuid);

    protected abstract int findIndex(String uuid);
}