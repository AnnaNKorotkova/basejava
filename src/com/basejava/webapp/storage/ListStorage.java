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
    protected void saveToStorage(Resume resume, Object o) {
        storage.add(resume);
    }

    @Override
    protected Resume getFromStorage(String uuid, Object o) {
        return storage.get(findKeyByElement(uuid));
    }

    @Override
    protected void deleteInStorage(String uuid, Object o) {
        storage.remove(findKeyByElement(uuid).intValue());
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
    protected boolean isContains(Object o) {
        return findKeyByElement((String) o)>=0;
    }

    @Override
    protected void updateInStorage(Resume resume, Object o) {
        storage.set(storage.indexOf(resume), resume);
    }
}