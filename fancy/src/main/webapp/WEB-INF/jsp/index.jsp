<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Hospital</title>
</head>
<body>
<link href="<c:url value="/resources/static/css/main.css" />" rel="stylesheet">

<div class="container">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3>Patients List</h3>
        </div>

        <div class="panel-body">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th width="14%">ID</th>
                    <th width="14%">Name</th>
                    <th width="14%">Date of birth</th>
                    <th width="14%">Date of arrival</th>
                    <th width="14%">Date of leave</th>
                    <th width="14">Current status</th>
                    <th width="14%"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="patient" items="${patients}">
                    <tr>
                        <td>${patient.id}</td>
                        <td>${patient.name}</td>
                        <td>${patient.dateOfBirth}</td>
                        <td>${patient.dateOfArrival}</td>
                        <td>${patient.dateOfLeave}</td>
                        <td>${patient.currentStatus}</td>
                        <td>
                            <a type="button" class="button"
                               href="${pageContext.request.contextPath}
                                        /update-patient?id=${patient.id}">Update</a>
                            <a type="button" class="button"
                               href="${pageContext.request.contextPath}
                                        /send-to-morgue?id=${patient.id}">Send</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>