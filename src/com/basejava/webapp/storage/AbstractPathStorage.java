package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {

    private Path directory;

    protected abstract void fileWrite(Resume resume, OutputStream path) throws IOException;

    protected abstract Resume readResume(InputStream f) throws IOException;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "Directory can't be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "does't directory or does't writealbe");
        }
    }

    @Override
    protected void updateInStorage(Resume resume, Path path) {
        try {
            fileWrite(resume, Files.newOutputStream(path));
        } catch (IOException e) {
            throw new StorageException("IO error", path.toString(), e);
        }
    }

    @Override
    protected void saveToStorage(Resume resume, Path path) {
        try {
            Files.createFile(path);
            fileWrite(resume, Files.newOutputStream(path));
        } catch (IOException e) {
            throw new StorageException("IO error", path.toString(), e);
        }
    }

    @Override
    protected Resume getFromStorage(Path path) {
        try {
            return readResume(Files.newInputStream(path));
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void deleteInStorage(Path path) {
        path.toFile().delete();
    }

    @Override
    protected Path findKeyByElement(String uuid) {
        return Paths.get(uuid);
    }

    @Override
    protected boolean isContains(Path path) {
        return Files.exists(path);
    }

    @Override
    protected List<Resume> getList() {
        List<Resume> resumes = new ArrayList<>();
        try {
            List<String> files = Files.readAllLines(directory);
            for (String f : files) {
                resumes.add(readResume(Files.newInputStream(Paths.get(f))));
            }
        } catch (IOException e) {
            throw new StorageException("The directory can't found", directory.getFileName().toString(), e);
        }
        return resumes;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteInStorage);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int)Files.size(directory);
        } catch (IOException e) {
            throw new StorageException("The directory doesn't exist", directory.toString(), e);
        }
    }
}