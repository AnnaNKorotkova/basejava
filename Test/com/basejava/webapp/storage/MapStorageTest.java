package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapStorageTest extends AbstractStorageTest {

    protected MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    @Override
    void getAllTest() {
        Resume[] resumes = {r1, r2, r3};
        boolean contains = (storage.get("uuid1") != null && storage.get("uuid2") != null && storage.get("uuid3") != null);
        assertEquals(resumes.length, storage.size());
        assertEquals(true, contains);
    }
}