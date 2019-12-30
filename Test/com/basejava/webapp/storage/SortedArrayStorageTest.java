package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortedArrayStorageTest extends AbstractArrayStorageTest {
    private AbstractArrayStorage storage = new SortedArrayStorage();

    public SortedArrayStorageTest(Storage storage) {
        super(storage);
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(new Resume("uuid1"));
        storage.save(new Resume("uuid2"));
        storage.save(new Resume("uuid3"));
    }

    @Test
    void saveResume() {
        Resume[] resume = {new Resume("uuid1"), new Resume("uuid2"), new Resume("uuid22"),
                new Resume("uuid3")};
        storage.save(new Resume("uuid22"));
        assertArrayEquals(resume, storage.getAll());
    }

    @Test
    void deleteResume() {
        Resume[] resume = {new Resume("uuid2"), new Resume("uuid3")};
        storage.delete("uuid1");
        assertArrayEquals(resume, storage.getAll());
    }
}