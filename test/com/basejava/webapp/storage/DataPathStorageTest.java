package com.basejava.webapp.storage;

import com.basejava.webapp.storage.strategy.DataStreamStrategy;

class DataPathStorageTest extends AbstractStorageTest {

    protected DataPathStorageTest() {
        super(new PathStorage(DIR, new DataStreamStrategy()));
    }
}