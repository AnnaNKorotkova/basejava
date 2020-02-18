package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.storage.strategy.ObjectStreamStrategy;

class ObjectPathStorageTest extends AbstractStorageTest {

    protected ObjectPathStorageTest() {
        super(new PathStorage(Config.get().getStorageDir().getPath(), new ObjectStreamStrategy()));
    }
}