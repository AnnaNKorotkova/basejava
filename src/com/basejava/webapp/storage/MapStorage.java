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
    protected void saveToStorage(Resume resume, Object o) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getFromStorage(Object key) {
        return storage.get(key);
    }

    @Override
    protected void deleteInStorage(String uuid, Object o) {
        storage.remove(uuid);
    }

    @Override
    protected String findKeyByElement(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isContains(Object key) {
        return storage.containsKey(key);
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
    protected void updateInStorage(Resume resume, Object o) {
        storage.replace(resume.getUuid(), resume);
    }
}