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
    public void updateStorageElement(int index, Resume resume, String uuid) {
        storage.set(index, resume);
    }

    @Override
    public void saveStorageElement(Resume resume, int index) {
        storage.add(resume);
        System.out.println("Resume id = \"" + resume.getUuid() + "\" is created");
    }

    @Override
    public Resume getStorageElement(int index, String uuid) {
        return storage.get(index);
    }

    @Override
    public void removeStorageElement(int index, String uuid) {
        storage.remove(index);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    protected int findIndex(String uuid) {
        int index = -1;
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                index = i;
            }
        }
        return index;
    }
}