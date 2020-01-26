package com.basejava.webapp.model;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    private final String uuid;
    private String fullName;
    private Map<Contact, String> contactSection;
    private Map<TypeSection, AbstractSection> resumeSection;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), Objects.requireNonNull(fullName, "Name can't be null"));
    }

    public Resume(String uuid, String fullName) {
        this.uuid = Objects.requireNonNull(uuid, "uuid can't be null");
        this.fullName = Objects.requireNonNull(fullName, "Name can't be null");
    }

    public Resume(String fullName, Map<Contact, String> contactSection, Map<TypeSection, AbstractSection> resumeSection) {
        this.uuid = UUID.randomUUID().toString();
        this.fullName = Objects.requireNonNull(fullName, "Name can't be null");
        this.contactSection = contactSection;
        this.resumeSection = resumeSection;
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
    public int hashCode() {
        int hash = 17;
        hash = hash*37 + uuid.hashCode();
        hash = hash*37 + fullName.hashCode();
        return hash;
    }

    @Override
    public int compareTo(Resume resume) {
        int result = fullName.compareTo(resume.fullName);
        return result == 0 ? uuid.compareTo(resume.uuid) : result;
    }

    @Override
    public String toString() {
        return "Resume{" + '\n' +
                "uuid='" + uuid + '\'' + '\n' +
                "fullName='" + fullName + '\'' + '\n' +
                "contactSection=" + contactSection + '\n' +
                "resumeSection=" + resumeSection + '\n' +
                '}';
    }
}