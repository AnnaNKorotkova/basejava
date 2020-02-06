package com.basejava.webapp.storage;

import com.basejava.webapp.storage.strategy.ObjectStreamStrategy;

class FileStorageTest extends AbstractStorageTest {

    protected FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStrategy()));
    }
}