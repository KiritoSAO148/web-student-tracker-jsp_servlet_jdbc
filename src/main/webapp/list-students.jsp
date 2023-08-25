<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*, com.example.web.jdbc.Student" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
<%--    <link type="text/css" rel="stylesheet" href="css/style.css">--%>
    <style>
        html, body{
            margin-left:15px; margin-right:15px;
            padding:0px;
            font-family:Verdana, Arial, Helvetica, sans-serif;
        }

        table {
            border-collapse:collapse;
            border-bottom:1px solid gray;
            font-family: Tahoma,Verdana,Segoe,sans-serif;
            width:72%;
        }

        th {
            border-bottom:1px solid gray;
            background:none repeat scroll 0 0 #0775d3;
            padding:10px;
            color: #FFFFFF;
        }

        tr {
            border-top:1px solid gray;
            text-align:center;
        }

        tr:nth-child(even) {background: #FFFFFF}
        tr:nth-child(odd) {background: #BBBBBB}

        #wrapper {width: 100%; margin-top: 0px; }
        #header {width: 70%; background: #0775d3; margin-top: 0px; padding:15px 0px 15px 15px;}
        #header h2 {width: 100%; margin:auto; color: #FFFFFF;}
        #container {width: 100%; margin:auto}
        #container h3 {color: #000;}
        #container #content {margin-top: 20px;}

        .add-student-button {
            border: 1px solid #666;
            border-radius: 5px;
            padding: 4px;
            font-size: 12px;
            font-weight: bold;
            width: 120px;
            padding: 5px 10px;

            margin-bottom: 15px;
            background: #cccccc;
        }
    </style>
    <title>Student Tracker App</title>
</head>

<%
    List<Student> theStudents = (List<Student>) request.getAttribute("STUDENT_LIST");
%>

<body>
    <div id="wrapper">
        <div id="header">
            <h2>FooBar University</h2>
        </div>
    </div>

    <div id="container">
        <div id="content">
            <input type="button" value="Add Student"
                    onclick="window.location.href='add-student-form.jsp'; return false;"
                   class="add-student-button"
            >
            <table>
                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                </tr>
                <c:forEach var="tempStudent" items="${STUDENT_LIST}">
                <tr>
                    <td>${tempStudent.firstName}</td>
                    <td>${tempStudent.lastName}</td>
                    <td>${tempStudent.email}</td>
                </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</body>
</html>