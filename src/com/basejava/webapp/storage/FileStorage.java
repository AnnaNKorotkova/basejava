package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {

    private File directory;
    protected SerializableStream serializableStream;


    protected FileStorage(File directory, SerializableStream serializableStream) {
        this.serializableStream = serializableStream;
        Objects.requireNonNull(directory, "Directory can't be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " doesn't directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " doesn't readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected void updateInStorage(Resume resume, File file) {
        saveToStorage(resume, file);
    }

    @Override
    protected void saveToStorage(Resume resume, File file) {
        try {
            file.createNewFile();
            serializableStream.fileWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume getFromStorage(File file) {
        isNullFiles();
        try {
            return serializableStream.readResume(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getPath(), e);
        }
    }

    @Override
    protected void deleteInStorage(File file) {
        isNullFiles();
        file.delete();
    }

    @Override
    protected File findKeyByElement(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isContains(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> getList() {
        isNullFiles();
        List<Resume> listResume = new ArrayList<>();
        File[] listFiles = directory.listFiles();
        for (File f : listFiles) {
            listResume.add(getFromStorage(f));
        }
        return listResume;
    }

    @Override
    public void clear() {
        isNullFiles();
        File[] listFiles = directory.listFiles();
        for (File f : listFiles) {
            deleteInStorage(f);
        }
    }

    @Override
    public int size() {
        int size = 0;
        File[] listFiles = directory.listFiles();
        for (File f : listFiles) {
            size++;
        }
        return size;
    }

    private void isNullFiles() {
        if (directory.listFiles() == null) {
            throw new StorageException("This directory is null", null);
        }
    }
}