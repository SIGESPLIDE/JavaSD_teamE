<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/BASE001.jsp/">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ログアウト</title>
    <style>
        /* Basic styling - you can customize this */
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            margin-top: 50px;
        }
        h2 {
            color: #333;
        }
        .logout-message {
            background-color: #d4edda; /* Light green */
            color: #155724; /* Dark green */
            padding: 10px;
            margin: 20px auto;
            border: 1px solid #c3e6cb;
            border-radius: 5px;
            width: 300px; /* Adjust as needed */
        }
        .login-link {
            margin-top: 30px;
        }
    </style>
</head>
<body>
    <h2>ログアウト</h2>

    <p class="logout-message">ログアウトしました</p>

    <div class="login-link">
        <a href="LOGI001.jsp">ログイン</a> <%-- **Placeholder: Replace "login.jsp" with your actual login page URL** --%>
    </div>
</body>
</html>
</c:import>