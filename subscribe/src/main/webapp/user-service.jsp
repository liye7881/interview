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
       <td colspan="3">
           <a href="/subscribe/subscribe/oper-user-service?oper=add&user_id=${param.user_id}&service_id=${param.service_id}">Add</a>
       </td>
    </tr>
    <tr>
        <th width="40%">User</th>
        <th width="40%">Service</th>
        <th width="20%">Oper</th>
    </tr>
    <c:forEach items="${userServices}" var="var">
        <tr>
            <td><c:out value="${var.user.name}" /></td>
            <td><c:out value="${var.service.name}" /></td>
            <td>
                <a href="/subscribe/subscribe/oper-user-service?oper=del&rela_id=${var.relaId}&user_id=${param.user_id}&service_id=${param.service_id}">Del</a>
            </td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="3" align="right">
            <a href="/subscribe/subscribe/user_service?page=${page - 1}">Pre</a>
            <a href="/subscribe/subscribe/user_service?page=${page + 1}">Next</a>
        </td>
    </tr>
</table>
</body>
</html>
