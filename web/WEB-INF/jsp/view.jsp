<%@ page import="java.time.format.DateTimeFormatter" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach items="${resume.contactSection}" var="contactEntry">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.basejava.webapp.model.Contact, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>

    <table cellpadding="2">
        <c:forEach items="${resume.resumeSection}" var="section">
            <jsp:useBean id="section" type="java.util.Map.Entry<com.basejava.webapp.model.TypeSection
                    , com.basejava.webapp.model.AbstractSection>"/>
            <tr>
                <td colspan="2"><h2><a name="type.name">${section.key.name}</a></h2></td>
            </tr>
            <c:choose>
                <c:when test="${section.key eq 'PERSONAL' || section.key eq 'OBJECTIVE'}">
                    <c:set var="textSection" value="${section.value}"/>
                    <jsp:useBean id="textSection" type="com.basejava.webapp.model.TextSection"/>
                    <tr>
                        <td colspan="2">
                            ${textSection.textContainer}
                        </td>
                    </tr>
                </c:when>
                <c:when test="${section.key eq 'ACHIEVEMENT' || section.key eq 'QUALIFICATIONS'}">
                    <c:set var="listSection" value="${section.value}"/>
                    <jsp:useBean id="listSection" type="com.basejava.webapp.model.ListSection"/>
                    <c:forEach items="${listSection.textList}" var="text">
                        <tr>
                            <td colspan="2">
                                <ul>
                                    <li>${text}</li>
                                </ul>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:when test="${section.key eq 'EXPERIENCE' || section.key eq 'EDUCATION'}">
                    <c:set var="timeLineSection" value="${section.value}"/>
                    <jsp:useBean id="timeLineSection" type="com.basejava.webapp.model.TimeLineSection"/>
                    <c:forEach items="${timeLineSection.listTimeLine}" var="list">
                        <c:set var="link" value="${list.homePage}"/>
                        <jsp:useBean id="link" type="com.basejava.webapp.model.Link"/>

                        <tr>
                            <td colspan="2">
                                <h3><a href="${link.url}">${link.name}</a></h3>
                            </td>
                        </tr>
                        <c:forEach items="${list.listItem}" var="item">
                            <jsp:useBean id="item" type="com.basejava.webapp.model.TimeLine.Item"/>
                            <tr>
                                <td width="15%"
                                    style="vertical-align: top">${item.startDate.format(DateTimeFormatter.ofPattern("MM/YYYY"))}
                                    - ${item.lastDate.format(DateTimeFormatter.ofPattern("MM/YYYY"))}
                                </td>
                                <td><b>${item.activity}</b>.<br>${item.description}</td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>