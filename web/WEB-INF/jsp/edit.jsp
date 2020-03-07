<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.basejava.webapp.model.Contact" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size="50" value="${resume.fullName}"></dd>
        </dl>
        <h3>Котакты:</h3>
        <c:forEach items="<%=Contact.values()%>" var="type">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size="30" value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr>


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
                            <input type="text" name="${section.key.name}" size="100"
                                   value="${textSection.textContainer}">

                        </td>
                    </tr>
                </c:when>

                <c:when test="${section.key eq 'ACHIEVEMENT' || section.key eq 'QUALIFICATIONS'}">
                    <c:set var="listSection" value="${section.value}"/>
                    <jsp:useBean id="listSection" type="com.basejava.webapp.model.ListSection"/>
                    <c:set var="count" value="0"/>
                    <c:forEach items="${listSection.textList}" var="text">
                        <tr>
                            <td colspan="2">
                                <ul>
                                        ${section.key}_${count}<!-- Удалить -->
                                    <input type="text" name="${section.key.name}_${count}" size="100" value="${text}">
                                    <c:set var="count" value="${count+1}"/>
                                </ul>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>

                <c:when test="${section.key eq 'EXPERIENCE' || section.key eq 'EDUCATION'}">
                    <c:set var="timeLineSection" value="${section.value}"/>
                    <jsp:useBean id="timeLineSection" type="com.basejava.webapp.model.TimeLineSection"/>
                    <c:set var="countLink" value="0"/>
                    <c:forEach items="${timeLineSection.listTimeLine}" var="list">
                        <c:set var="link" value="${list.homePage}"/>
                        <jsp:useBean id="link" type="com.basejava.webapp.model.Link"/>

                        <tr>
                            <td width="15%" style="vertical-align: top">
                                <ul>
                                    <input type="text" name="${countLink}_name" size="50" value="${link.name}"
                                           placeholder="Назнание">
                                    <input type="text" name="${countLink}_url" size="50" value="${link.url}"
                                           placeholder="URL">
                                </ul>
                            </td>
                        </tr>
                        <c:set var="countItem" value="0"/>
                        <c:forEach items="${list.listItem}" var="item">
                            <jsp:useBean id="item" type="com.basejava.webapp.model.TimeLine.Item"/>

                            <tr>
                                <td width="15%" style="vertical-align: top">
                                    <ul>
                                        <input type="text" name="${countLink}_startDate_${countItem}" size="6"
                                               value="${item.startDate.format(DateTimeFormatter.ofPattern("YYYY-MM"))}"
                                               placeholder="YYYY-MM">
                                        <input type="text" name="${countLink}_lastDate_${countItem}" size="6"
                                               value="${item.lastDate.format(DateTimeFormatter.ofPattern("YYYY-MM"))}"
                                               placeholder="YYYY-MM">
                                    </ul>
                                </td>
                                <td>
                                    <input type="text" name="${countLink}_activity_${countItem}" size="50"
                                           value="${item.activity}" placeholder="activity">
                                    <input type="text" name="${countLink}_description_${countItem}" size="50"
                                           value="${item.description}" placeholder="описание">
                                </td>
                            </tr>
                            <c:set var="countItem" value="${countItem+1}"/>
                        </c:forEach>
                        <c:set var="countLink" value="${countLink+1}"/>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
