package com.basejava.webapp.storage.strategy;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;

import com.basejava.webapp.util.DateUtil;

import java.util.*;

public class DataStreamStrategy implements SerializableStream {

    @Override
    public void fileWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<Contact, String> contacts = resume.getContactSection();
            dos.writeInt(contacts.size());
            for (Map.Entry<Contact, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<TypeSection, AbstractSection> resumeSection = resume.getResumeSection();
            dos.writeInt(resumeSection.size());
            for (Map.Entry<TypeSection, AbstractSection> entry : resumeSection.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) entry.getValue()).getTextContainer());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        dos.writeInt(((ListSection) entry.getValue()).getTextList().size());
                        for (String ts : ((ListSection) entry.getValue()).getTextList()) {
                            dos.writeUTF(ts);
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        dos.writeInt(((TimeLineSection) entry.getValue()).getListTimeLine().size());
                        for (TimeLine tl : ((TimeLineSection) entry.getValue()).getListTimeLine()) {
                            dos.writeInt(tl.getListItem().size());
                            setLink(tl, dos);
                            for (TimeLine.Item tli : tl.getListItem()) {
                                setDate(tli.getStartDate(), dos);
                                setDate(tli.getLastDate(), dos);
                                dos.writeUTF(tli.getActivity());
                                if (tli.getDescription() != null) {
                                    dos.writeUTF(tli.getDescription());
                                } else {
                                    dos.writeUTF("");
                                }
                            }
                        }
                }
            }
        }
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Map<Contact, String> resumeContactSection = new EnumMap<>(Contact.class);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resumeContactSection.put(Contact.valueOf(dis.readUTF()), dis.readUTF());
            }

            Map<TypeSection, AbstractSection> resumeSection = new EnumMap<>(TypeSection.class);
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                String typeSection = dis.readUTF();
                TypeSection typeSectionValue = TypeSection.valueOf(typeSection);
                switch (typeSection) {
                    case "PERSONAL":
                    case "OBJECTIVE":
                        resumeSection.put(typeSectionValue, new TextSection(dis.readUTF()));
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        int listSize = dis.readInt();
                        List<String> ls = new ArrayList<>();
                        for (int j = 0; j < listSize; j++) {
                            ls.add(dis.readUTF());
                        }
                        resumeSection.put(typeSectionValue, new ListSection(ls));
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        int listSize1 = dis.readInt();
                        List<TimeLine> tl = new ArrayList<>();
                        for (int k = 0; k < listSize1; k++) {
                            int listTliSize = dis.readInt();
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            List<TimeLine.Item> tli = new ArrayList<>();
                            for (int n = 0; n < listTliSize; n++) {
                                tli.add(new TimeLine.Item(
                                        DateUtil.of(dis.readInt(), Month.of(dis.readInt()))
                                        , DateUtil.of(dis.readInt(), Month.of(dis.readInt()))
                                        , dis.readUTF()
                                        , dis.readUTF())
                                );
                            }
                            tl.add(new TimeLine(name, url, tli));
                        }
                        resumeSection.put(typeSectionValue, new TimeLineSection(tl));
                        break;
                }
            }
            return new Resume(uuid, fullName, resumeContactSection, resumeSection);
        }
    }

    private void setDate(LocalDate ld, DataOutputStream dos) throws IOException {
        dos.writeInt(ld.getYear());
        dos.writeInt(ld.getMonthValue());
    }

    private void setLink(TimeLine tl, DataOutputStream dos) throws IOException {
        dos.writeUTF(tl.getHomePage().getName());
        if (tl.getHomePage().getUrl() != null) {
            dos.writeUTF(tl.getHomePage().getUrl());
        } else {
            dos.writeUTF("");
        }
    }
}