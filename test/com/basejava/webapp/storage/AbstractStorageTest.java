package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.basejava.webapp.ResumeTestData.*;
import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractStorageTest {
    protected final static File STORAGE_DIR = Config.get().getStorageDir();
    protected final static String DB_URL = Config.get().getDbUrl();
    protected final static String DB_USER = Config.get().getDbUser();
    protected final static String DB_PASSWORD = Config.get().getDbPassword();
    protected Storage storage;
    private final static  String  UUID_1= UUID.randomUUID().toString();
    private final static  String  UUID_2= UUID.randomUUID().toString();
    private final static  String  UUID_3= UUID.randomUUID().toString();
    private final static  String  UUID_4= UUID.randomUUID().toString();

    protected Resume r1 = createResume(UUID_1, "Иванов");
    protected Resume r2 = createResume(UUID_2, "Сидоров");
    protected Resume r3 = createResume(UUID_3, "Петров");
    protected Resume r4 = createResume(UUID_4, "Сидоров");

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
        Resume resume = createResume(UUID_1, "Ivanov");
        storage.update(resume);
        assertEquals(resume, storage.get(UUID_1));
    }

    @Test
    void updateNotExistTest() {
        assertThrows(NotExistStorageException.class, () -> {
            storage.update(createResume("uuid6", "Pukin"));
        });
    }

    @Test
    void getTest() {
        assertEquals(r1, storage.get(UUID_1));
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
        assertEquals(r4, storage.get(UUID_4));
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
        storage.delete(UUID_2);
        assertEquals(2, storage.size());
        assertThrows(NotExistStorageException.class, () -> {
            storage.get(UUID_2);
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