package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractArrayStorageTest {
    private Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(new Resume("uuid1"));
        storage.save(new Resume("uuid2"));
        storage.save(new Resume("uuid3"));
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void update() {
        Resume resume = new Resume("uuid1");
        storage.update(resume);
        assertEquals(resume, storage.get("uuid1"));
    }

    @Test
    void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> {
            storage.update(new Resume("uuid6"));
        });
    }

    @Test
    void save() {
        storage.save(new Resume("uuid5"));
        assertEquals(4, storage.size());
    }

    @Test
    void saveOverflow() {
        for (int i = 0; i < 9997; i++) {
            storage.save(new Resume());
        }
        assertThrows(StorageException.class, () -> {
            storage.save(new Resume());
        });
    }

    @Test
    void saveAlreadyExist() {
        assertThrows(ExistStorageException.class, () -> {
            storage.save(new Resume("uuid1"));
        });
    }

    @Test
    void get() {
        Resume resume = new Resume("uuid1");
        assertEquals(resume, storage.get("uuid1"));
    }

    @Test
    void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> {
            storage.get("dummy");
        });
    }

    @Test
    void delete() {
        storage.delete("uuid1");
        assertEquals(2, storage.size());
    }

    @Test
    void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> {
            storage.delete("dummy");
        });
    }

    @Test
    void getAll() {
        Resume[] resume = {new Resume("uuid1"), new Resume("uuid2"), new Resume("uuid3")};
        assertArrayEquals(resume, storage.getAll());
    }

    @Test
    void size() {
        assertEquals(3, storage.size());
    }
}