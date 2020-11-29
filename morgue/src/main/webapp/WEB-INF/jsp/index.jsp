<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Morgue</title>
</head>
<body>
<link href="<c:url value="/resources/static/css/main.css" />" rel="stylesheet">
<div class="container">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3>Morgue List</h3>
        </div>
        <div class="button">
            <a type="button" class="button"
               href="/morgue">Update</a>
        </div>
        <div class="button">
            <a type="button" class="button"
               href="/">Main Page</a>
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
                </tr>
                </thead>
                <tbody>
                <c:forEach var="patient" items="${patient}">
                    <tr>
                        <td>${patient.id}</td>
                        <td>${patient.name}</td>
                        <td>${patient.dateOfBirth}</td>
                        <td>${patient.dateOfArrival}</td>
                        <td>${patient.dateOfLeave}</td>
                        <td>${patient.currentStatus}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>