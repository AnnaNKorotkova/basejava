<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.basejava.webapp.model.Contact" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <script
            src="https://code.jquery.com/jquery-3.4.1.slim.js"
            integrity="sha256-BTlTdQO9/fascB1drekrDVkaKd9PkwBymMlHOiG+qLI="
            crossorigin="anonymous"></script>
    <script src="js/addForm.js" type="text/javascript"></script>
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form id="form" method="post" action="resume" enctype="application/x-www-form-urlencoded">
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
                <td><h2><a name="type.name">${section.key.name}</a></h2></td>
            </tr>
            <c:choose>
                <c:when test="${section.key eq 'PERSONAL' || section.key eq 'OBJECTIVE'}">
                    <c:set var="textSection" value="${section.value}"/>
                    <jsp:useBean id="textSection" type="com.basejava.webapp.model.TextSection"/>
                    <tr>
                        <td>

                            <input type="text" name="${section.key}" size="120"
                                   value="${textSection.textContainer}">
                        </td>
                    </tr>
                </c:when>
                <c:when test="${section.key eq 'ACHIEVEMENT' || section.key eq 'QUALIFICATIONS'}">
                    <c:set var="listSection" value="${section.value}"/>
                    <jsp:useBean id="listSection" type="com.basejava.webapp.model.ListSection"/>
                    <c:set var="count" value="0"/>
                    <c:forEach items="${listSection.textList}" var="text">

                        <div id=${section.key}>
                            <tr>
                                <td>
                                    <div>

                                            <textarea name="${section.key}_${count}" cols="120"
                                                      rows="3">${text}</textarea>
                                                <c:set var="count" value="${count+1}"/>
                                            <c:if test="${count == listSection.textList.size()}">
                                            <button id="${count}" type="button" class="addButton"><img
                                                    src="img/add.png"></button>
                                            </c:if>
                                            </dl>
                                    </div>
                                </td>
                            </tr>
                        </div>
                    </c:forEach>
                </c:when>
                <c:when test="${section.key eq 'EXPERIENCE' || section.key eq 'EDUCATION'}">
                    <c:set var="timeLineSection" value="${section.value}"/>
                    <jsp:useBean id="timeLineSection" type="com.basejava.webapp.model.TimeLineSection"/>
                    <c:set var="countLink" value="0"/>

                    <div id=${section.key}>
                        <c:forEach items="${timeLineSection.listTimeLine}" var="list">
                            <c:set var="link" value="${list.homePage}"/>
                            <jsp:useBean id="link" type="com.basejava.webapp.model.Link"/>

                            <tr>
                                <td>
                                    <dl>
                                        <input type="text" name="${section.key}_${countLink}_name" size="120"
                                               value="${link.name}"
                                               placeholder="Назнание">
                                        <button type="button" class="addButton"><img src="img/add.png"></button>

                                    </dl>
                                    <dl>
                                        <input type="text" name="${section.key}_${countLink}_url" size="120"
                                               value="${link.url}"
                                               placeholder="URL">
                                    </dl>
                                </td>
                            </tr>
                            <c:set var="countItem" value="0"/>
                            <c:forEach items="${list.listItem}" var="item">
                                <jsp:useBean id="item" type="com.basejava.webapp.model.TimeLine.Item"/>
                                <div>
                                    <tr>
                                        <td width="15%" style="vertical-align: top">
                                            <dl>
                                                <label for="start">Начало периода:</label>
                                                <input type="text" id="start"
                                                       name="${section.key}_${countLink}_startDate_${countItem}"
                                                       size="6"
                                                       value="${item.startDate.format(DateTimeFormatter.ofPattern("YYYY-MM"))}"
                                                       placeholder="YYYY-MM">
                                                <label for="end">Конец периода:</label>
                                                <input type="text" id="end"
                                                       name="${section.key}_${countLink}_lastDate_${countItem}"
                                                       size="6"
                                                       value="${item.lastDate.format(DateTimeFormatter.ofPattern("YYYY-MM"))}"
                                                       placeholder="YYYY-MM">
                                                <lable for="period">Добавить еще один период:</lable>
                                                <button id="period" type="button" class="addButton"><img
                                                        src="img/add.png"></button>
                                            </dl>
                                        </td>
                                        <td>
                                            <dl>
                                                <input type="text"
                                                       name="${section.key}_${countLink}_activity_${countItem}"
                                                       size="120"
                                                       value="${item.activity}" placeholder="Должность">
                                            </dl>
                                            <dl>
                                        <textarea name="${section.key}_${countLink}_description_${countItem}"
                                                  cols="120" rows="3"
                                                  placeholder="Описание">${item.description}</textarea>
                                            </dl>
                                        </td>
                                    </tr>
                                </div>
                                <br>
                                <c:set var="countItem" value="${countItem+1}"/>
                            </c:forEach>
                            <c:set var="countLink" value="${countLink+1}"/>
                        </c:forEach>
                    </div>
                </c:when>
            </c:choose>
        </c:forEach>
        <br><br>
        <button type="submit" class="confirmButton">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>