<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Services</title>
    <jsp:include page="style.jsp" />
</head>
<body>
<jsp:include page="header.jsp" />
<table border="1" width="50%">
    <tr>
        <th width="60%">Name</th>
        <th width="40%">Oper</th>
    </tr>
    <c:forEach items="${services}" var="service">
        <tr>
            <td><c:out value="${service.name}" /></td>
            <td>
                <a href="/subscribe/subscribe/user-service?oper=list&service_id=${service.serviceId}">User</a>
            </td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="2" align="right">
            <a href="/subscribe/subscribe/service?page=${page - 1}">Pre</a>
            <a href="/subscribe/subscribe/service?page=${page + 1}">Next</a>
        </td>
    </tr>
</table>
</body>
</html>
