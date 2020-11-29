<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hospital</title>
</head>
<body>
<h3>Send patient with status 1 to morgue</h3>
<form action="${pageContext.request.contextPath}
        /send-to-morgue?id=${patient}" method="post">
    <button type="submit">Send</button>
</form>
</body>
</html>