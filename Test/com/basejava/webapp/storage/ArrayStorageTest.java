package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayStorageTest extends AbstractArrayStorageTest{

    public ArrayStorageTest() {
        super(new ArrayStorage());
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