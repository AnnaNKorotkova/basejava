package com.basejava.webapp.storage.strategy;

import com.basejava.webapp.model.*;
import com.basejava.webapp.util.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonStreamSerializer implements SerializableStream {

    @Override
    public void fileWrite(Resume resume, OutputStream os) throws IOException {
        try (Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            JsonParser.write(resume, w);
        }
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        try (Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return JsonParser.read(r, Resume.class);
        }
    }
}
