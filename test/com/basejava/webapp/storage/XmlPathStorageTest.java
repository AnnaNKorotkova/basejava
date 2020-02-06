package com.basejava.webapp.storage;

import com.basejava.webapp.storage.strategy.XmlStreamSerializer;

class XmlPathStorageTest extends AbstractStorageTest {

    protected XmlPathStorageTest() {
        super(new PathStorage(DIR, new XmlStreamSerializer()));
    }
}