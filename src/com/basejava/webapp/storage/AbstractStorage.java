package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public abstract void clear();

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            updateArray(index, resume);
            System.out.println("Resume id = \"" + resume.getUuid() + "\" is updated");
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public abstract void updateArray(int index, Resume resume);

    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            saveRes(resume, index);
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    public abstract void saveRes(Resume resume, int index);

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return getResume(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public abstract Resume getResume(int index);

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            removeResume(index);
            System.out.println("Resume id = \"" + uuid + "\" is deleted");
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public abstract void removeResume(int index);

    public abstract Resume[] getAll();

    public abstract int size();

    protected abstract int findIndex(String uuid);
}