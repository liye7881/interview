<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add</title>
    <jsp:include page="style.jsp" />
</head>
<body>
<form action="/subscribe/subscribe/oper-user-service" method="post">
    <table border="1" width="50%">
        <tr>
            <th width="40%">Check</th>
            <th width="60%">Name</th>
        </tr>
        <c:forEach items="${services}" var="service">
            <tr>
                <td>
                    <input type="checkbox" name="service_${service.serviceId}"/>
                </td>
                <td><c:out value="${service.name}" /></td>
            </tr>
        </c:forEach>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>
                    <input type="checkbox" name="user_${user.userId}"/>
                </td>
                <td><c:out value="${user.name}" /></td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="3" align="right">
                <a href="/subscribe/subscribe/oper-user-service?oper=add&page=${page - 1}&user_id=${param.user_id}&service_id=${param.service_id}">Pre</a>
                <a href="/subscribe/subscribe/oper-user-service?oper=add&page=${page + 1}&user_id=${param.user_id}&service_id=${param.service_id}">Next</a>
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <button type="submit">Add</button>
            </td>
        </tr>
    </table>
    <input type="hidden" name="oper" value="${param.oper}"/>
    <input type="hidden" name="user_id" value="${param.user_id}"/>
    <input type="hidden" name="service_id" value="${param.service_id}"/>
</form>
</body>
</html>
