package com.basejava.webapp.util;

import com.basejava.webapp.model.*;
import com.basejava.webapp.util.DateUtil;

import java.time.Month;
import java.util.*;

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
                    resume.addSection(tp,new TextSection(""));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    List<String> qualification = new ArrayList<>();
                    qualification.add("");
                    resume.addSection(tp, new ListSection(qualification));
                    break;

                case EXPERIENCE:
                case EDUCATION:
                    List<TimeLine> listEdu = new ArrayList<>();
                    listEdu.add(new TimeLine("", "", new ArrayList<>(Collections.singletonList(new TimeLine.Item(DateUtil.of(1111, Month.NOVEMBER), DateUtil.of(1111, Month.NOVEMBER), "", "")))));
                    resume.addSection(tp, new TimeLineSection(listEdu));
                    break;
            }
        }
        return resume;
    }
}
