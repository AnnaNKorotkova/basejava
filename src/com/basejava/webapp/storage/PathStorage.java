package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.strategy.SerializableStream;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {

    private Path directory;
    protected SerializableStream serializableStream;

    protected PathStorage(String dir, SerializableStream serializableStream) {
        this.serializableStream = serializableStream;
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "Directory can't be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "does't directory or does't writealbe");
        }
    }

    @Override
    protected void updateInStorage(Resume resume, Path path) {
        try {
            serializableStream.fileWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IO error", path.toString(), e);
        }
    }

    @Override
    protected void saveToStorage(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("I/O error", null, e);
        }
        updateInStorage(resume, path);
    }

    @Override
    protected Resume getFromStorage(Path path) {
        try {
            return serializableStream.readResume(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void deleteInStorage(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("This path doesn't exisis", null, e);
        }
    }

    @Override
    protected Path findKeyByElement(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isContains(Path path) {
        return Files.exists(path);
    }

    @Override
    protected List<Resume> getList() {
        Stream<Path> files = getStream();
        return files.map(this::getFromStorage).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getStream().forEach(this::deleteInStorage);
    }

    @Override
    public int size() {
        return (int) getStream().count();
    }

    private Stream<Path> getStream() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("I/O error", null, e);
        }
    }
}