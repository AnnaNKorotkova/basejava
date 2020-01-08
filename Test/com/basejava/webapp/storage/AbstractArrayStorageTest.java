package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
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