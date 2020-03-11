package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.Storage;
import com.basejava.webapp.util.BlankResume;
import com.basejava.webapp.util.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class ResumeServlet extends HttpServlet {

    Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume;
        if (uuid != null && uuid.trim().length() != 0) {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
            fillResume(resume, request);
            storage.update(resume);
        } else {
            resume = new Resume(fullName);
            fillResume(resume, request);
            storage.save(resume);
        }
        response.sendRedirect("resume");
    }

    private void fillResume(Resume resume, HttpServletRequest request) {
        for (Contact type : Contact.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            } else {
                resume.getContactSection().remove(type);
            }
        }

        for (TypeSection type : TypeSection.values()) {
            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    String value = request.getParameter(type.name());
                    if (value != null && value.trim().length() != 0) {
                        resume.addSection(type, new TextSection(value));
                    } else {
                        resume.getResumeSection().remove(type);
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    int countTS = 0;
                    List<String> list = new ArrayList<>();
                    String valueTs = request.getParameter(type.name() + '_' + countTS);
                    while (valueTs != null) {
                        if (valueTs.trim().length() != 0) {
                            list.add(valueTs);
                        }
                        valueTs = request.getParameter(type.name() + '_' + ++countTS);
                    }
                    if (list.size() != 0) {
                        resume.addSection(type, new ListSection(list));
                    } else {
                        resume.getResumeSection().remove(type);
                    }
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    int countLs = 0;
                    List<TimeLine> listTimeLine = new ArrayList<>();
                    String name = request.getParameter(type.name() + '_' + countLs + "_name");
                    while (name != null) {
                        if (name.trim().length() != 0) {
                            name = request.getParameter(type.name() + '_' + countLs + "_name");
                            String url = request.getParameter(type.name() + '_' + countLs + "_url");
                            int countItem = 0;
                            String activity = request.getParameter(type.name() + '_' + countLs + "_activity_" + countItem);
                            List<TimeLine.Item> listItems = new ArrayList<>();
                            while (activity != null) {
                                String sd = request.getParameter(type.name() + '_' + countLs + "_startDate_" + countItem);
                                String ld = request.getParameter(type.name() + '_' + countLs + "_lastDate_" + countItem);
                                if (sd.length() != 0) {
                                    int sdy = parseInt(sd.substring(0, 4));
                                    int sdm = parseInt(sd.substring(5));
                                    LocalDate startDate = DateUtil.of(sdy, Month.of(sdm));
                                    LocalDate lastDate;
                                    if (ld.length() == 0) {
                                        lastDate = LocalDate.now();
                                    } else {
                                        int ldy = parseInt(ld.substring(0, 4));
                                        int ldm = parseInt(ld.substring(5));
                                        lastDate = DateUtil.of(ldy, Month.of(ldm));
                                    }
                                    String description = request.getParameter(type.name() + '_' + countLs + "_description_" + countItem);
                                    listItems.add(new TimeLine.Item(startDate, lastDate, activity, description));
                                }
                                countItem++;
                                activity = request.getParameter(type.name() + '_' + countLs + "_activity_" + countItem);
                            }
                            listTimeLine.add(new TimeLine(name, url, listItems));
                        }
                        countLs++;
                        name = request.getParameter(type.name() + '_' + countLs + "_name");
                    }
                    resume.addSection(type, new TimeLineSection(listTimeLine));
                    break;
            }

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("listResume", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                resume = storage.get(uuid);
                for (TypeSection type : TypeSection.values()) {

                    switch (type) {
                        case PERSONAL:
                        case OBJECTIVE:
                            if (resume.getResumeSection().get(type) == null) {
                                resume.addSection(type, TextSection.EMPTY);
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (resume.getResumeSection().get(type) == null) {
//                                List<String> list = new ArrayList();
//                                list.add("");
                                resume.addSection(type, ListSection.EMPTY);
                            } else {
                                ListSection section = (ListSection) resume.getResumeSection().get(type);
                                section.addToList("");
                                resume.addSection(type, section);
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            TimeLineSection tls = (TimeLineSection) resume.getResumeSection().get(type);
                            if (tls.getListTimeLine().size() == 0) {
                                resume.addSection(type, new TimeLineSection(TimeLine.EMPTY));
                            } else {
                                for (TimeLine timeLine : tls.getListTimeLine()) {
                                    timeLine.getListItem().add(TimeLine.Item.EMPTY);
                                }
                                tls.getListTimeLine().add(TimeLine.EMPTY);
                                resume.addSection(type, tls);
                            }
                            break;
                    }
                }
                break;
            case "save":
                resume = BlankResume.createBlankResume();
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                .forward(request, response);
    }
}