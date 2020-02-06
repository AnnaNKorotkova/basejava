package com.basejava.webapp.storage;

import com.basejava.webapp.storage.strategy.ObjectStreamStrategy;

class PathStorageTest extends AbstractStorageTest {

    protected PathStorageTest() {
        super(new PathStorage(DIR, new ObjectStreamStrategy()));
    }
}