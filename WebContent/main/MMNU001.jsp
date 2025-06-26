<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:import url="/BASE001.jsp">
	<c:param name="head">
		<title>メインメニュー</title>
	</c:param>
	<c:param name="body">
	<h2>仮メインメニュー</h2>
	<a href="${pageContext.request.contextPath}/main/STDM001">学生管理一覧</a>
	<li><a href="${pageContext.request.contextPath}/main/subject" >科目管理</a></li>
	</c:param>
</c:import>
