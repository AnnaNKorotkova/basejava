package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            System.out.println("Resume id = \"" + resume.getUuid() + "\" is updated");
        } else {
            System.out.println("Resume id = \"" + resume.getUuid() + "\" is not found");
        }
    }

    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            System.out.println("There is not enough space to create a new resume");
        } else {
            int index = findIndex(resume.getUuid());
            if (index >= 0) {
                System.out.println("Resume id = \"" + resume.getUuid() + "\" is already exist, try to update");
            } else {
                saveResume(resume, index);
                size++;
                System.out.println("Resume id = \"" + resume.getUuid() + "\" is created");
            }
        }
    }

    protected abstract void saveResume(Resume resume, int index);

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("Resume id = \"" + uuid + "\" is not found");
            return null;
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            deleteResume(uuid, index);
            storage[size - 1] = null;
            size--;
            System.out.println("Resume id = \"" + uuid + "\" is deleted");
        } else {
            System.out.println("Resume id = \"" + uuid + "\" is not found");
        }
    }

    public abstract void deleteResume(String uuid, int index);

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected abstract int findIndex(String uuid);
}