package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.basejava.webapp.ResumeTestData.*;
import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractStorageTest {
    protected final static String DIR = "/home/mc/basejava/storage";
    protected final static File STORAGE_DIR = new File(DIR);
    protected Storage storage;
    protected Resume r1 = createResume("uuid1", "Иванов");
    protected Resume r2 = createResume("uuid2", "Сидоров");
    protected Resume r3 = createResume("uuid3", "Петров");
    protected Resume r4 = createResume("uuid4", "Сидоров");

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
        Resume resume = createResume("uuid1", "Ivanov");
        storage.update(resume);
        assertEquals(resume, storage.get("uuid1"));
    }

    @Test
    void updateNotExistTest() {
        assertThrows(NotExistStorageException.class, () -> {
            storage.update(createResume("uuid6", "Pukin"));
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
        List<Resume> resumes = Arrays.asList(r1, r2, r3);
        resumes.sort(Resume::compareTo);
        assertEquals(resumes.size(), storage.size());
        assertEquals(resumes, storage.getAllSorted());
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
        storage.delete("uuid2");
        assertEquals(2, storage.size());
        assertThrows(NotExistStorageException.class, () -> {
            storage.get("uuid2");
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