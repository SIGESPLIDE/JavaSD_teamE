<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/BASE001.jsp">
<c:param name="head">
	<title>ログアウト</title>
</c:param>

<c:param name="body">
	<h2>ログアウト</h2>
	<p class="logout-message">ログアウトしました</p>
	<div class="login-link">
		<a href="LOGI001.jsp">ログイン</a>
		<%-- **Placeholder: Replace "login.jsp" with your actual login page URL** --%>
	</div>
</c:param>
</c:import>