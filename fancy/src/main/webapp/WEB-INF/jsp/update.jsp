<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hospital</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="panel-heading">Update Patient</div>
        <div class="panel body">
            <form:form method="post" modelAttribute="patient">
                <form:hidden path="id"/>
                <form:hidden path="name"/>
                <form:hidden path="dateOfBirth"/>
                <form:hidden path="dateOfArrival"/>
                <fieldset class="form-group">
                    <form:label path="dateOfLeave">Date of leave</form:label>
                    <form:input path="dateOfLeave" type="text" class="form-control"/>
                    <form:errors path="dateOfLeave" cssClass="text-warning"/>
                </fieldset>
                <fieldset class="form-group">
                    <form:label path="currentStatus">Current status</form:label>
                    <form:input path="currentStatus" type="text" class="form-control"
                                required="required"/>
                    <form:errors path="currentStatus" cssClass="text-warning"/>
                </fieldset>
                <button type="submit" class="btn btn-success">Save</button>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>