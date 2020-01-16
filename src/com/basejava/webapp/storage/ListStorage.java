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
    protected void saveToStorage(Resume resume, Object index) {
        storage.add(resume);
    }

    @Override
    protected Resume getFromStorage(Object index) {
        return storage.get((int) index);
    }

    @Override
    protected void deleteInStorage(Object index) {
        storage.remove((int) index);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    protected Integer findKeyByElement(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isContains(Object index) {
        return (int) index >= 0;
    }

    @Override
    protected void updateInStorage(Resume resume, Object index) {
        storage.set((int) index, resume);
    }
}