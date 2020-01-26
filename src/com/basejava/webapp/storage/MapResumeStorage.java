package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage <Resume>{

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void saveToStorage(Resume resume, Resume key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getFromStorage(Resume key) {
        return key;
    }

    @Override
    protected void deleteInStorage(Resume key) {
        storage.remove(key.getUuid());
    }

    @Override
    protected Resume findKeyByElement(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isContains(Resume key) {
        return key != null;
    }

    @Override
    public List<Resume> getList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void updateInStorage(Resume resume, Resume key) {
        storage.replace(key.getUuid(), resume);
    }
}