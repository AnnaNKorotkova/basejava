package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

abstract class AbstractArrayStorageTest {

    protected Storage storage;
    protected Resume r1 = new Resume("uuid1");
    protected Resume r2 = new Resume("uuid2");
    protected Resume r3 = new Resume("uuid3");

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @Test
    @Order(1)
    void updateTest() {
        Resume resume = new Resume("uuid1");
        storage.update(resume);
        assertEquals(resume, storage.get("uuid1"));
    }

    @Test
    @Order(2)
    void updateNotExistTest() {
        assertThrows(NotExistStorageException.class, () -> {
            storage.update(new Resume("uuid6"));
        });
    }

    @Test
    @Order(3)
    void getTest() {
        Resume resume = new Resume("uuid1");
        assertEquals(resume, storage.get("uuid1"));
    }

    @Test
    @Order(4)
    void getNotExistTest() {
        assertThrows(NotExistStorageException.class, () -> {
            storage.get("dummy");
        });
    }

    @Test
    @Order(5)
    void getAllTest() {
        Resume[] resume = {new Resume("uuid1"), new Resume("uuid2"), new Resume("uuid3")};
        assertEquals(resume.length, storage.size());
    }

    @Test
    @Order(6)
    void sizeTest() {
        assertEquals(3, storage.size());
    }

    @Test
    @Order(7)
    void saveTest() {
        Resume resume = new Resume("uuid5");
        storage.save(resume);
        assertEquals(resume, storage.get("uuid5"));
    }

    @Test
    @Order(8)
    void saveAlreadyExistTest() {
        assertThrows(ExistStorageException.class, () -> {
            storage.save(new Resume("uuid1"));
        });
    }

    @Test
    @Order(9)
    void deleteTest() {
        storage.delete("uuid1");
        assertEquals(2, storage.size());
    }

    @Test
    @Order(10)
    void deleteNotExistTest() {
        assertThrows(NotExistStorageException.class, () -> {
            storage.delete("dummy");
        });
    }

    @Test
    @Order(11)
    void clearTest() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    @Order(12)
    void saveOverflowTest() {
        storage.clear();
        for (int i = 0; i < 10000; i++) {
            storage.save(new Resume());
        }
        assertThrows(StorageException.class, () -> {
            storage.save(new Resume());
        });
    }
}