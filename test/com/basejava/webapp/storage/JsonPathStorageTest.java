package com.basejava.webapp.storage;

import com.basejava.webapp.storage.strategy.JsonStreamSerializer;

class JsonPathStorageTest extends AbstractStorageTest {

    protected JsonPathStorageTest() {
        super(new PathStorage(DIR, new JsonStreamSerializer()));
    }
}