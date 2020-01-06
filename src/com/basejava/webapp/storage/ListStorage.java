package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.removeAll(storage);
    }

    @Override
    public void updateArray(int index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    public void saveRes(Resume resume, int index) {
        storage.add(resume);
        System.out.println("Resume id = \"" + resume.getUuid() + "\" is created");
    }

    @Override
    public Resume getResume(int index) {
        return storage.get(index);
    }

    @Override
    public void removeResume(int index) {
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
        Resume resume = new Resume(uuid);
        int index = -1;
        for (int i = 0; i < storage.size(); i++) {
            if (resume.hashCode() == storage.get(i).hashCode()) {
                index = i;
            }
        }
        return index;
    }
}