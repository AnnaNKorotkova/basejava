package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.*;

public class HashMapStorage extends AbstractStorage {

    private Map<String,Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void saveToStorage(Resume resume, Object key) {
        storage.put((String) key, resume);
    }

    @Override
    protected Resume getFromStorage(Object key) {
        return storage.get(key);
    }

    @Override
    protected void deleteInStorage(Object key) {
        storage.remove(key);
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
    public List<Resume> getList() {
        List<Resume> resumes = new ArrayList<>(storage.values());
        return resumes;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void updateInStorage(Resume resume, Object key) {
        storage.replace((String) key, resume);
    }
}