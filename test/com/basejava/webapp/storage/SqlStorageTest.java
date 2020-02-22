package com.basejava.webapp.storage;

import com.basejava.webapp.sql.SqlHelper;

class SqlStorageTest extends AbstractStorageTest{

    protected SqlStorageTest() {
        super(new SqlStorage());
    }
}