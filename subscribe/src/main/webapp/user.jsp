<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Users</title>
    <jsp:include page="style.jsp" />
</head>
<body>
<jsp:include page="header.jsp" />
<table border="1" width="50%">
    <tr>
        <th width="60%">Name</th>
        <th width="40%">Oper</th>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td><span><c:out value="${user.name}" /></span></td>
            <td>
                <a href="/subscribe/subscribe/user-service?oper=list&user_id=${user.userId}">Service</a>
            </td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="2" align="right">
            <a href="/subscribe/subscribe/user?page=${page - 1}">Pre</a>
            <a href="/subscribe/subscribe/user?page=${page + 1}">Next</a>
        </td>
    </tr>
</table>
</body>
</html>
