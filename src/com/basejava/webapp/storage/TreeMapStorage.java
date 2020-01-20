package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapStorage extends AbstractStorage {

    private Map<String, Resume> storage = new TreeMap<>();

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
    protected List<Resume> getList() {
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