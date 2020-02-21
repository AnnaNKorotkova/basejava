package com.basejava.webapp.storage;

class SqlStorageTest extends AbstractStorageTest{

    protected SqlStorageTest() {
        super(new SqlStorage(new SqlHelper(DB_URL, DB_USER, DB_PASSWORD)));
    }
}