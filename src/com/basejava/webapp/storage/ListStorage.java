package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void saveToStorage(Resume resume, Integer index) {
        storage.add(resume);
    }

    @Override
    protected Resume getFromStorage(Integer index) {
        return storage.get(index);
    }

    @Override
    protected void deleteInStorage(Integer index) {
        storage.remove((int) index);
    }

    @Override
    protected List<Resume> getList() {
        return storage;
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
    protected boolean isContains(Integer index) {
        return index >= 0;
    }

    @Override
    protected void updateInStorage(Resume resume, Integer index) {
        storage.set(index, resume);
    }
}