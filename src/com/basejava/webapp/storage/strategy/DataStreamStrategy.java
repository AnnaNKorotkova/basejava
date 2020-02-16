package com.basejava.webapp.storage.strategy;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;

import java.util.*;

public class DataStreamStrategy implements SerializableStream {

    @Override
    public void fileWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<Contact, String> contacts = resume.getContactSection();
            writePart(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<TypeSection, AbstractSection> resumeSection = resume.getResumeSection();
            writePart(dos, resumeSection.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) entry.getValue()).getTextContainer());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> ls = ((ListSection) entry.getValue()).getTextList();
                        writePart(dos, ls, dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<TimeLine> ltl = ((TimeLineSection) entry.getValue()).getListTimeLine();
                        writePart(dos, ltl, tl -> {
                            setLink(tl, dos);
                            writePart(dos, tl.getListItem(), tli ->
                            {
                                setDate(tli.getStartDate(), dos);
                                setDate(tli.getLastDate(), dos);
                                dos.writeUTF(tli.getActivity());
                                String desc = tli.getDescription();
                                if (desc != null) {
                                    dos.writeUTF(desc);
                                } else {
                                    dos.writeUTF(" ");
                                }
                            });
                        });
                }
            });
        }
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Map<Contact, String> resumeContactSection = new EnumMap<>(Contact.class);
            readPart(dis, () -> resumeContactSection.put(Contact.valueOf(dis.readUTF()), dis.readUTF()));

            Map<TypeSection, AbstractSection> resumeSection = new EnumMap<>(TypeSection.class);
            readPart(dis, () -> {
                TypeSection typeSection = TypeSection.valueOf(dis.readUTF());
                resumeSection.put(typeSection, readTypeOfSection(dis, typeSection));
            });

            return new Resume(uuid, fullName, resumeContactSection, resumeSection);
        }
    }

    private void setDate(LocalDate ld, DataOutputStream dos) throws IOException {
        dos.writeInt(ld.getYear());
        dos.writeInt(ld.getMonthValue());
    }

    private LocalDate readDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), Month.of(dis.readInt()), 1);
    }

    private void setLink(TimeLine tl, DataOutputStream dos) throws IOException {
        dos.writeUTF(tl.getHomePage().getName());
        if (tl.getHomePage().getUrl() != null) {
            dos.writeUTF(tl.getHomePage().getUrl());
        } else {
            dos.writeUTF("");
        }
    }

    private AbstractSection readTypeOfSection(DataInputStream dis, TypeSection type) throws IOException {
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(readList(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new TimeLineSection(readList(dis, () -> new TimeLine(new Link(dis.readUTF(), dis.readUTF()),
                        readList(dis, () -> new TimeLine.Item(readDate(dis), readDate(dis), dis.readUTF(), dis.readUTF())))));

            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    private interface ElementForWrite<T> {
        void write(T a) throws IOException;
    }

    private <T> void writePart(DataOutputStream dos, Collection<T> collection, ElementForWrite<T> element) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            element.write(item);
        }
    }

    private interface ElementForRead<T> {
        T read() throws IOException;
    }

    private void readPart(DataInputStream dis, InsertElement inserter) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            inserter.insert();
        }
    }

    private interface InsertElement {
        void insert() throws IOException;
    }

    private <T> List<T> readList(DataInputStream dis, ElementForRead<T> reader) throws IOException {
        List<T> list = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            T read = reader.read();
            list.add(read);
        }
        return list;
    }
}