package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void saveToStorage(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getFromStorage(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteInStorage(String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected Object findElement(String uuid) {
        return null;
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isContains(String uuid) {
        return storage.containsKey(uuid);
    }

    @Override
    protected void updateInStorage(Resume resume) {
        storage.replace(resume.getUuid(), resume);
    }
}