package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private final static Comparator<Resume> UUID_COMPARE = Comparator.comparing(Resume::getUuid);

    @Override
    public void saveResume(Resume resume, Integer index) {
        int newIndex = -(index + 1);
        System.arraycopy(storage, newIndex, storage, newIndex + 1, size - newIndex);
        storage[newIndex] = resume;
    }

    @Override
    public void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
    }

    @Override
    protected Integer findKeyByElement(String uuid) {
        Resume searchKey = new Resume(uuid, "");
        return Arrays.binarySearch(storage, 0, size, searchKey, UUID_COMPARE);
    }
}