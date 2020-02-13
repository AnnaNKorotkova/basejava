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


    private Map<TypeSection, AbstractSection> resumeSection = new EnumMap<>(TypeSection.class);

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

    public String getFullName() {
        return fullName;
    }

    public Map<Contact, String> getContactSection() {
        return contactSection;
    }

    public Map<TypeSection, AbstractSection> getResumeSection() {
        return resumeSection;
    }

//    public void addContact(Contact contact, String sectionText){
//        contactSection.put(contact, sectionText);
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contactSection, resume.contactSection) &&
                Objects.equals(resumeSection, resume.resumeSection);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        result = 31 * result + (contactSection != null ? contactSection.hashCode() : 0);
        result = 31 * result + (resumeSection != null ? resumeSection.hashCode() : 0);
        return result;
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