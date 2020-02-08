package com.basejava.webapp.storage;

import com.basejava.webapp.storage.strategy.ObjectStreamStrategy;

class ObjectPathStorageTest extends AbstractStorageTest {

    protected ObjectPathStorageTest() {
        super(new PathStorage(DIR, new ObjectStreamStrategy()));
    }
}