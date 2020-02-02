package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private File directory;
    private List<File> files = new ArrayList<>();

    protected abstract void fileWrite(Resume resume, OutputStream file) throws IOException;

    protected abstract Resume readResume(InputStream f) throws IOException;

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
        try {
            fileWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void saveToStorage(Resume resume, File file) {
        try {
            file.createNewFile();
            fileWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume getFromStorage(File file) {
        try {
            return readResume(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getPath(), e);
        }
    }

    @Override
    protected void deleteInStorage(File file) {
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
        List<File> files = recursiveSearch(directory);
        List<Resume> resumes = new ArrayList<>();
        for (File f : files) {
            try {
                resumes.add(readResume(new BufferedInputStream(new FileInputStream(f))));
            } catch (IOException e) {
                throw new StorageException("IO error", f.getPath(), e);
            }
        }
        files.clear();
        return resumes;
    }

    @Override
    public void clear() {
        List<File> files = recursiveSearch(directory);
        for (File f : files) {
            f.delete();
        }
        files.clear();
    }

    @Override
    public int size() {
        int size = recursiveSearch(directory).size();
        files.clear();
        return size;
    }

    private List<File> recursiveSearch(File file) {
        if (file.isDirectory()) {
            File[] listAll = Objects.requireNonNull(file.listFiles());
            for (File f : listAll) {
                if (f.isDirectory()) {
                    recursiveSearch(f);
                } else {
                    System.out.println(f.getPath());
                    files.add(f);
                }
            }
        }
        return files;
    }
}