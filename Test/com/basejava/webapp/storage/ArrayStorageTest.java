package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayStorageTest extends AbstractArrayStorageTest{
    public Storage storage = new ArrayStorage();

    public ArrayStorageTest(Storage storage) {
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
        Resume[] resume = {new Resume("uuid1"), new Resume("uuid2"), new Resume("uuid3"),
                new Resume("uuid4")};
        storage.save(new Resume("uuid4"));
        assertArrayEquals(resume, storage.getAll());
    }

    @Test
    void deleteResume() {
        Resume[] resume = {new Resume("uuid3"), new Resume("uuid2")};
        storage.delete("uuid1");
        assertArrayEquals(resume, storage.getAll());
    }
}