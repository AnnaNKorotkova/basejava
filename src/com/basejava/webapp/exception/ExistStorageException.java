package com.basejava.webapp.exception;

public class ExistStorageException extends StorageException {

    public ExistStorageException(String uuid) {
        super("Resume id = \"" + uuid + "\" already exist", uuid);
    }
}