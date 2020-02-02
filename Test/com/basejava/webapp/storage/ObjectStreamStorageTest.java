package com.basejava.webapp.storage;


class ObjectStreamStorageTest extends AbstractStorageTest{

    protected ObjectStreamStorageTest() {
        super(new ObjectStreamFileStorage(STORAGE_DIR));
    }
}