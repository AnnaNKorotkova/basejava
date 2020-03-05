<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Резюме</title>
</head>
<c:forEach items="${listResume}" var="x">
    <body>
    <h2><strong>${x.fullName}</strong></h2>
    <h4 id="h_99989867421582969268143"><em>Контакты</em></h4>
    <table border="0" style="height: 18px; width: 100%; border-collapse: collapse;">
        <c:forEach items="${x.contactSection}" var="t">
            <tbody>
            <tr style="height: 18px;">
                <td style="width: 13%; height: 18px;">
                        ${t.key.title}
                </td>
                <td style="width: 87%; height: 18px;">
                        ${t.value}
                </td>
            </tr>
            </tbody>
        </c:forEach>
    </table>
    <h4><em>Основная информация</em></h4>
    <table border="0" style="height: 18px; width: 100%; border-collapse: collapse;" cellpadding="10">
        <c:forEach items="${x.resumeSection}" var="r">
            <tbody>
            <tr style="height: 18px;" valign="top">
                <td style="width: 13%; height: 18px;">
                        ${r.key.name}
                </td>
                <td style="width: 87%; height: 18px;">
                    <c:choose>
                        <c:when test="${r.key eq 'PERSONAL' || r.key eq 'OBJECTIVE'}">
                            <li>${r.value}</li>
                        </c:when>
                        <c:when test="${r.key eq 'ACHIEVEMENT' || r.key eq 'QUALIFICATIONS'}">
                            <c:forEach items="${r.value.textList}" var="text">
                            <li>${text}</li>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                </td>
            </tr>
            </tbody>
        </c:forEach>
    </table>
    <p></p>
    <hr/>
    </body>
</c:forEach>
</html>
