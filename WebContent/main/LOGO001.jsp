<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/BASE001.jsp">
<c:param name="head">
	<title>ログアウト</title>
</c:param>

<c:param name="body">
    <%-- 画面タイトル --%>
	<h2 class="alert alert-secondary">ログアウト</h2>
	<p class="alert alert-success text-center">ログアウトしました</p>
	<%-- ログイン画面ヘ --%>
	<div class="login-link">
    <a href="${pageContext.request.contextPath}/login.action">ログイン</a>
		<%-- **Placeholder: Replace "login.jsp" with your actual login page URL** --%>
	</div>
</c:param>
</c:import>