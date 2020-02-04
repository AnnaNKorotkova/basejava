package com.basejava.webapp.storage;

class FileStorageTest extends AbstractStorageTest {

    protected FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStrategy()));
    }
}