package com.basejava.webapp.storage.strategy;

import com.basejava.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializableStream {

    void fileWrite(Resume resume, OutputStream os) throws IOException;

    Resume readResume(InputStream is) throws IOException;
}