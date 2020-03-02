package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.Contact;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.SqlStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        SqlStorage sqlStorage = new SqlStorage(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPassword());

        List<Resume> listResume = new ArrayList<>();
        PrintWriter out = response.getWriter();
        if (uuid == null) {
            listResume = sqlStorage.getAllSorted();
            print(out, listResume);
        } else {
            listResume.add(sqlStorage.get(uuid));
            print(out, listResume);
        }
        request.setAttribute("listResume", listResume);
//        getServletContext().getRequestDispatcher("/template.jsp").forward(request, response);
    }

    private void print(PrintWriter out, List<Resume> list) {
        out.println("<title>Резюме</title>");
        out.println("</head>");
        out.println("<body>");
        for (Resume r : list) {
            out.println("<h2><strong>" + r.getFullName() + "</strong></h2>");
            out.println(" <h4 id=\"h_99989867421582969268143\"><em>Контакты</em></h4>");
            out.println("<table border=\"0\" style=\"height: 18px; width: 100%; border-collapse: collapse;\">");
            for (Map.Entry<Contact, String> e : r.getContactSection().entrySet()) {
                out.println("<tbody>");
                out.println("<tr style=\"height: 18px;\">");
                out.println("<td style=\"width: 13%; height: 18px;\">" + e.getKey().name() + "</td>");
                out.println(" <td style=\"width: 87%; height: 18px;\">" + e.getValue() + "</td>");
                out.println("</tr>");
                out.println("</tbody>");
            }
            out.println("</table>");
            out.println(" <p></p>");
            out.println("<hr/>");
            out.println("</body>");
        }
    }
}