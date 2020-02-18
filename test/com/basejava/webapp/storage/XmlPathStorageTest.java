package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.storage.strategy.XmlStreamSerializer;

class XmlPathStorageTest extends AbstractStorageTest {

    protected XmlPathStorageTest() {
        super(new PathStorage(Config.get().getStorageDir().getPath(), new XmlStreamSerializer()));
    }
}