package com.basejava.webapp.util;

import com.basejava.webapp.model.*;

public class BlankResume {

    private BlankResume(){
    }

    public static Resume createBlankResume() {

        Resume resume = new Resume("", "");

        for (Contact c : Contact.values()) {
                    resume.addContact(c, "");
        }

        for (TypeSection tp : TypeSection.values()) {
            switch (tp) {
                case PERSONAL:
                case OBJECTIVE:
                    resume.addSection(tp, TextSection.EMPTY);
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    resume.addSection(tp, ListSection.EMPTY);
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    resume.addSection(tp, new TimeLineSection(TimeLine.EMPTY));
                    break;
            }
        }
        return resume;
    }
}
