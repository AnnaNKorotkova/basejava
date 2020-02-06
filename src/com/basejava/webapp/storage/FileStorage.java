package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.strategy.SerializableStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {

    private File directory;
    private SerializableStream serializableStream;


    protected FileStorage(File directory, SerializableStream serializableStream) {
        Objects.requireNonNull(directory, "Directory can't be null");

        this.serializableStream = serializableStream;
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
        try {
            serializableStream.fileWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void saveToStorage(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
        updateInStorage(resume, file);
    }

    @Override
    protected Resume getFromStorage(File file) {
        try {
            return serializableStream.readResume(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getPath(), e);
        }
    }

    @Override
    protected void deleteInStorage(File file) {
        if (!file.delete()) {
            throw new StorageException("The file haven't been deleted", file.getName());
        }
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
        List<Resume> listResume = new ArrayList<>();
        File[] listFiles = isNullFiles();
        for (File f : listFiles) {
            listResume.add(getFromStorage(f));
        }
        return listResume;
    }

    @Override
    public void clear() {
        File[] listFiles = isNullFiles();
        for (File f : listFiles) {
            deleteInStorage(f);
        }
    }

    @Override
    public int size() {
        return isNullFiles().length;
    }

    private File[] isNullFiles() {
        File[] listFiles = directory.listFiles();
        if (listFiles == null) {
            throw new StorageException("This directory is null", null);
        }
        return listFiles;
    }
}