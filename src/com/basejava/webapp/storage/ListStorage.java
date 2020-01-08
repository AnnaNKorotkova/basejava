package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void saveInStorage(Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getResumeInStorage(String uuid) {
        return storage.get(findIndex(uuid));
    }

    @Override
    protected void deleteResumeByUuid(String uuid) {
        storage.remove(findIndex(uuid));
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    private int findIndex(String uuid) {
        int index = -1;
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                index = i;
            }
        }
        return index;
    }

    @Override
    protected boolean isContains(String uuid) {
        return findIndex(uuid) >= 0;
    }

    @Override
    protected void updateInStorage(Resume resume) {
        storage.set(storage.indexOf(resume), resume);
    }
}