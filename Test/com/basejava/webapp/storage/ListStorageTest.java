package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListStorageTest {
    private ListStorage arrayList = new ListStorage();
    protected Resume r1 = new Resume("uuid1");
    protected Resume r2 = new Resume("uuid2");
    protected Resume r3 = new Resume("uuid3");
    protected Resume r4 = new Resume("uuid4");

    @BeforeEach
    public void setUp() {
        arrayList.save(r1);
        arrayList.save(r2);
        arrayList.save(r3);
    }

    @Test
    void clearTest() {
        arrayList.clear();
        assertEquals(0, arrayList.size());
    }

    @Test
    void updateTest() {
        Resume resume = new Resume("uuid1");
        arrayList.update(resume);
        assertEquals(resume, arrayList.get("uuid1"));
    }

    @Test
    void updateNotExistTest() {
        assertThrows(NotExistStorageException.class, () -> {
            arrayList.update(new Resume("uuid6"));
        });
    }

    @Test
    void saveTest() {
        arrayList.save(r4);
        assertEquals(r4, arrayList.get("uuid4"));
    }

    @Test
    void saveAlreadyExistTest() {
        assertThrows(ExistStorageException.class, () -> {
            arrayList.save(r1);
        });
    }

    @Test
    void getTest() {
        assertEquals(r3, arrayList.get("uuid3"));
    }

    @Test
    void getNotExistTest() {
        assertThrows(NotExistStorageException.class, () -> {
            arrayList.get("dummy");
        });
    }

    @Test
    void deleteTest() {
        arrayList.delete("uuid2");
        assertEquals(2, arrayList.size());
    }

    @Test
    void deleteNotExistTest() {
        assertThrows(NotExistStorageException.class, () -> {
            arrayList.delete("dummy");
        });
    }

    @Test
    void getAllTest() {
        Resume[] resumes = {r1, r2, r3};
        assertEquals(resumes.length, arrayList.size());
        assertArrayEquals(resumes, (Resume[]) arrayList.getAll());
    }

    @Test
    void sizeTest() {
        assertEquals(3, arrayList.size());
    }
}