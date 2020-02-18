package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.storage.strategy.DataStreamStrategy;

class DataPathStorageTest extends AbstractStorageTest {

    protected DataPathStorageTest() {
        super(new PathStorage(Config.get().getStorageDir().getPath(), new DataStreamStrategy()));
    }
}