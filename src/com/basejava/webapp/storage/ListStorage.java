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
    protected void saveToStorage(Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getFromStorage(String uuid) {
        return storage.get(findElement(uuid));
    }

    @Override
    protected void deleteInStorage(String uuid) {
        storage.remove(findElement(uuid).intValue());
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Integer findElement(String uuid) {
        Integer index = -1;
          for (Integer i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                index = i;
            }
        }
        return index;
    }

    @Override
    protected boolean isContains(String uuid) {
        return findElement(uuid) >= 0;
    }

    @Override
    protected void updateInStorage(Resume resume) {
        storage.set(storage.indexOf(resume), resume);
    }
}