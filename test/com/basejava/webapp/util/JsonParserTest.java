package com.basejava.webapp.util;

import com.basejava.webapp.model.AbstractSection;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.model.TextSection;
import com.basejava.webapp.model.TypeSection;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.basejava.webapp.ResumeTestData.createResume;
import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

    protected Resume r1 = createResume(UUID.randomUUID().toString(), "Иванов");


    @Test
    void read() {
        String json = JsonParser.write(r1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        assertEquals(r1, resume);
    }

    @Test
    void write() {
        AbstractSection section1 = new TextSection("Objective1");
        String json = JsonParser.write(section1);
        System.out.println(json);
        AbstractSection section2 = JsonParser.read(json, TextSection.class);
        assertEquals(section1, section2);
    }
}