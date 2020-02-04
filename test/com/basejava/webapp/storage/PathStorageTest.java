package com.basejava.webapp.storage;

class PathStorageTest extends AbstractStorageTest {

    protected PathStorageTest() {
        super(new PathStorage(DIR, new ObjectStreamStrategy()));
    }
}