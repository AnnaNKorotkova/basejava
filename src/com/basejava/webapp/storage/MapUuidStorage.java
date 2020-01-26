package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapUuidStorage extends AbstractStorage<String> {

    private Map<String, Resume> storage = new TreeMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void saveToStorage(Resume resume, String key) {
        storage.put(key, resume);
    }

    @Override
    protected Resume getFromStorage(String key) {
        return storage.get(key);
    }

    @Override
    protected void deleteInStorage(String key) {
        storage.remove(key);
    }

    @Override
    protected String findKeyByElement(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isContains(String key) {
        return storage.containsKey(key);
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void updateInStorage(Resume resume, String key) {
        storage.replace(key, resume);
    }
}