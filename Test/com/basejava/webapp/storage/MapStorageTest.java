package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapStorageTest {
    private MapStorage mapStorage = new MapStorage();
    protected Resume r1 = new Resume("uuid1");
    protected Resume r2 = new Resume("uuid2");
    protected Resume r3 = new Resume("uuid3");
    protected Resume r4 = new Resume("uuid4");

    @BeforeEach
    void setUp() {
        mapStorage.save(r1);
        mapStorage.save(r2);
        mapStorage.save(r3);
    }

    @Test
    void clearTest() {
        mapStorage.clear();
        assertEquals(0, mapStorage.size());
    }

    @Test
    void updateTest() {
        Resume resume = new Resume("uuid1");
        mapStorage.update(resume);
        assertEquals(resume, mapStorage.get("uuid1"));
    }

    @Test
    void updateNotExistTest() {
        assertThrows(NotExistStorageException.class, () -> {
            mapStorage.update(new Resume("uuid6"));
        });
    }

    @Test
    void saveTest() {
        mapStorage.save(r4);
        assertEquals(r4, mapStorage.get("uuid4"));
        assertEquals(4, mapStorage.size());
    }

    @Test
    void saveAlreadyExistTest() {
        assertThrows(ExistStorageException.class, () -> {
            mapStorage.save(r1);
        });
    }

    @Test
    void getTest() {
        assertEquals(r3, mapStorage.get("uuid3"));
    }

    @Test
    void getNotExistTest() {
        assertThrows(NotExistStorageException.class, () -> {
            mapStorage.get("dummy");
        });
    }

    @Test
    void deleteTest() {
        mapStorage.delete("uuid2");
        assertEquals(2, mapStorage.size());
    }

    @Test
    void deleteNotExistTest() {
        assertThrows(NotExistStorageException.class, () -> {
            mapStorage.delete("dummy");
        });
    }

    @Test
    void getAllTest() {
        Resume[] resumes = {r1, r2, r3};
        assertEquals(resumes.length, mapStorage.size());
    }

    @Test
    void sizeTest() {
        assertEquals(3, mapStorage.size());
    }
}