package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.Contact;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

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
            for (Contact type : Contact.values()) {
                String value = request.getParameter(type.name());
                if (value != null && value.trim().length() != 0) {
                    resume.addContact(type, value);
                } else {
                    resume.getContactSection().remove(type);
                }
            }

            storage.update(resume);
        } else {
            resume = new Resume(fullName);
            for (Contact type : Contact.values()) {
                String value = request.getParameter(type.name());
                if (value != null && value.trim().length() != 0) {
                    resume.addContact(type, value);
                } else {
                    resume.getContactSection().remove(type);
                }
            }
            storage.save(resume);
        }
        response.sendRedirect("resume");
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
                break;
            case "save":
                resume = new Resume();
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                .forward(request, response);
    }
}