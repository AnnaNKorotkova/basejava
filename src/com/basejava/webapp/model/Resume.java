package com.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

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

    public Resume(String uuid, String fullName, Map<Contact, String> contactSection) {
        this(uuid, fullName);
        this.contactSection = contactSection;
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

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Map<Contact, String> getContactSection() {
        return contactSection;
    }

    public String getContact(Contact contact) {
        return contactSection.get(contact);
    }

    public Map<TypeSection, AbstractSection> getResumeSection() {
        return resumeSection;
    }

    public void addContact(Contact type, String value) {
        contactSection.put(type, value);
    }

    public void addSection(TypeSection type, AbstractSection section) {
        resumeSection.put(type, section);
    }
//
//    public <T> void addSectionValue(TypeSection type, T object) {
//        switch (type) {
//            case PERSONAL:
//            case OBJECTIVE:
//                resumeSection.putIfAbsent(type, new TextSection((String) object));
//                break;
//            case ACHIEVEMENT:
//            case QUALIFICATIONS:
//                if (resumeSection.get(type) == null) {
//                    resumeSection.put(type, new ListSection(Collections.singletonList((String) object)));
//                } else {
//                    ListSection section = (ListSection) resumeSection.get(type);
//                    section.addToList((String) object);
//                    resumeSection.put(type, section);
//                }
//                break;
//            case EXPERIENCE:
//            case EDUCATION:
//                TimeLineSection tls = (TimeLineSection) resumeSection.get(type);
//                if (tls.getListTimeLine().size() == 0) {
//                    resumeSection.put(type, (TimeLine) object);
//                } else {
//                    for (TimeLine timeLine : tls.getListTimeLine()) {
//                        timeLine.getListItem().add(TimeLine.Item.EMPTY); //здесь недороботочка
//                    }
//                    tls.getListTimeLine().add((TimeLine) object);
//                    resumeSection.put(type, tls);
//                }
//                break;
//        }
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
