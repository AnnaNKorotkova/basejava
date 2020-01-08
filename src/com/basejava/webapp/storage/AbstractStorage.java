package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        if (isContains(resume.getUuid())) {
            updateInStorage(resume);
            System.out.println("Resume id = \"" + resume.getUuid() + "\" is updated");
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    protected abstract boolean isContains(String uuid);

    protected abstract void updateInStorage(Resume resume);

    public void save(Resume resume) {
        if (!isContains(resume.getUuid())) {
            saveInStorage(resume);
            System.out.println("Resume id = \"" + resume.getUuid() + "\" is created");
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    protected abstract void saveInStorage(Resume resume);

    public Resume get(String uuid) {
        if (isContains(uuid)) {
            return getResumeInStorage(uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract Resume getResumeInStorage(String uuid);

    @Override
    public void delete(String uuid) {
        if (isContains(uuid)) {
            deleteResumeByUuid(uuid);
            System.out.println("Resume id = \"" + uuid + "\" is deleted");
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void deleteResumeByUuid(String uuid);
}