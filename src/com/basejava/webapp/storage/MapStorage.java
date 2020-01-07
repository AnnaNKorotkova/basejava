package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    Map<String, Resume> storage = new HashMap<>();

    @Override
    public void updateStorageElement(int index, Resume resume, String uuid) {
        storage.replace(uuid, resume);
    }

    @Override
    public void saveStorageElement(Resume resume, int index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public Resume getStorageElement(int index, String uuid) {
        return storage.get(uuid);
    }

    @Override
    public void removeStorageElement(int index, String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected int findIndex(String uuid) {
        if (storage.get(uuid) == null) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
