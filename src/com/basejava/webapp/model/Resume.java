package com.basejava.webapp.model;

import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    private final String uuid;
    private String fullName;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), Objects.requireNonNull(fullName, "Name can't be null"));
    }

    public Resume(String uuid, String fullName) {
        this.uuid = Objects.requireNonNull(uuid, "uuid can't be null");
        this.fullName = Objects.requireNonNull(fullName, "Name can't be null");
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid);
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public int compareTo(Resume resume) {
        int result = fullName.compareTo(resume.fullName);
        return result == 0 ? uuid.compareTo(resume.uuid) : result;
    }
}