package com.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {

    private final static long serialVersionUID = 1L;
    private String uuid;
    private String fullName;
    private Map<Contact, String> contactSection = new EnumMap<>(Contact.class);
    private Map<TypeSection, AbstractSection> resumeSection = new EnumMap<>(TypeSection.class);;

    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), Objects.requireNonNull(fullName, "Name can't be null"));
    }

    public Resume(String fullName, Map<Contact, String> contactSection, Map<TypeSection, AbstractSection> resumeSection) {
        this(UUID.randomUUID().toString(), fullName);
        this.contactSection = contactSection;
        this.resumeSection = resumeSection;
    }

    public Resume(String uuid, String fullName) {
        this.uuid = Objects.requireNonNull(uuid, "uuid can't be null");
        this.fullName = Objects.requireNonNull(fullName, "Name can't be null");
    }

    public Resume(String uuid, String fullName, Map<Contact, String> contactSection, Map<TypeSection, AbstractSection> resumeSection) {
        this(uuid, fullName);
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
        hash = hash * 37 + uuid.hashCode();
        hash = hash * 37 + fullName.hashCode();
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