package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
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

    }

    @Override
    protected void saveToStorage(Resume resume, File file) {
        try {
            file.createNewFile();
            fileWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }

    }

    protected abstract void fileWrite(Resume resume, File file) throws IOException;

    @Override
    protected Resume getFromStorage(File file) {
        return null;
    }

    @Override
    protected void deleteInStorage(File file) {

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
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        return 0;
    }
}