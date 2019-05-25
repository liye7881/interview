<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error</title>
    <jsp:include page="style.jsp" />
</head>
<body>
<jsp:include page="header.jsp" />
<h3>Something went wrong!</h3>
<h3><c:out value="${msg}" /></h3>
</body>
</html>
