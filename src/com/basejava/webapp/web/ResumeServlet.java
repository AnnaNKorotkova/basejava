package com.basejava.webapp.web;

import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.SqlStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        SqlStorage sqlStorage = new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "postgres");

        List<Resume> listResume = new ArrayList<>();
        if (uuid == null) {
            listResume = sqlStorage.getAllSorted();

        } else {
            listResume.add(sqlStorage.get(uuid));
        }
        request.setAttribute("listResume", listResume);
        getServletContext().getRequestDispatcher("/template.jsp").forward(request, response);
    }
}