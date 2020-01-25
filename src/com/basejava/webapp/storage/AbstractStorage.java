package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<K> implements Storage {

    private static final Logger LOG =  Logger.getLogger(AbstractStorage.class.getName());

    protected abstract void updateInStorage(Resume resume, K key);

    protected abstract void saveToStorage(Resume resume, K key);

    protected abstract Resume getFromStorage(K key);

    protected abstract void deleteInStorage(K key);

    protected abstract K findKeyByElement(String uuid);

    protected abstract boolean isContains(K key);

    protected abstract List<Resume> getList();

    public void update(Resume resume) {
        updateInStorage(resume, checkNotExistException(resume.getUuid()));
        LOG.info("Resume id = \"" + resume.getUuid() + "\" is updated");
    }

    public void save(Resume resume) {
        saveToStorage(resume, checkExistException(resume.getUuid()));
        LOG.info("Resume id = \"" + resume.getUuid() + "\" is created");
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
        LOG.info("Resume id = \"" + uuid + "\" is deleted");
    }

    private K checkExistException(String uuid) {
        K key = findKeyByElement(uuid);
        if (isContains(key)) {
            LOG.warning("Resume id = \"" + uuid + "\" already exist");
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private K checkNotExistException(String uuid) {
        K key = findKeyByElement(uuid);
        if (!isContains(key)) {
            LOG.warning("Resume id = \"" + uuid + "\" does not exist");
            throw new NotExistStorageException(uuid);
        }
        return key;
    }
}