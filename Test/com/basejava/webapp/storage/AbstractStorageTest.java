package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractStorageTest {
    protected Storage storage;
    protected Resume r1 = new Resume("uuid1");
    protected Resume r2 = new Resume("uuid2");
    protected Resume r3 = new Resume("uuid3");
    protected Resume r4 = new Resume("uuid4");

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @Test
    void updateTest() {
        Resume resume = new Resume("uuid1");
        storage.update(resume);
        assertEquals(resume, storage.get("uuid1"));
    }

    @Test
    void updateNotExistTest() {
        assertThrows(NotExistStorageException.class, () -> {
            storage.update(new Resume("uuid6"));
        });
    }

    @Test
    void getTest() {
        assertEquals(r1, storage.get("uuid1"));
    }

    @Test
    void getNotExistTest() {
        assertThrows(NotExistStorageException.class, () -> {
            storage.get("dummy");
        });
    }

    @Test
    void getAllTest() {
        Resume[] resumes = {r1, r2, r3};
        assertEquals(resumes.length, storage.size());
    }

    @Test
    void sizeTest() {
        assertEquals(3, storage.size());
    }

    @Test
    void saveTest() {
        storage.save(r4);
        assertEquals(r4, storage.get("uuid4"));
        assertEquals(4, storage.size());
    }

    @Test
    void saveAlreadyExistTest() {
        assertThrows(ExistStorageException.class, () -> {
            storage.save(r1);
        });
    }

    @Test
    void deleteTest() {
        storage.delete("uuid1");
        assertEquals(2, storage.size());
        assertThrows(NotExistStorageException.class, () -> {
            storage.get("uuid1");
        });
    }

    @Test
    void deleteNotExistTest() {
        assertThrows(NotExistStorageException.class, () -> {
            storage.delete("dummy");
        });
    }

    @Test
    void clearTest() {
        storage.clear();
        assertEquals(0, storage.size());
    }
}