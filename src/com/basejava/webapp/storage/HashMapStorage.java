package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.*;

public class HashMapStorage extends AbstractStorage {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void saveToStorage(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getFromStorage(Object key) {
        return (Resume) key;
    }

    @Override
    protected void deleteInStorage(Object key) {
        storage.remove(((Resume) key).getUuid());
    }

    @Override
    protected Resume findKeyByElement(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isContains(Object key) {
        return key != null;
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
        storage.replace(resume.getUuid(), resume);
    }
}