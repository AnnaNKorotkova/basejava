package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.storage.strategy.JsonStreamSerializer;

class JsonPathStorageTest extends AbstractStorageTest {

    protected JsonPathStorageTest() {
        super(new PathStorage(Config.get().getStorageDir().getPath(), new JsonStreamSerializer()));
    }
}