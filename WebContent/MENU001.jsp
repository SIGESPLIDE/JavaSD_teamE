<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/main/css/style-menu.css">
<c:choose>
    <%-- ログインしている場合（セッションにユーザー情報がある） --%>
    <c:when test="${not empty user}">
		<nav class="sidebar">
		    <ul class="side-bar">
		        <li><a href="${pageContext.request.contextPath}/menu.action">メニュー</a></li>
		        <li><a href="${pageContext.request.contextPath}/main/STDM001">学生管理</a></li>
		        <li>成績管理</li>
		        <li class="indent"><a href="${pageContext.request.contextPath}/main/ExamRegist">成績登録</a></li>
		        <li class="indent"><a href="${pageContext.request.contextPath}/main/ExamList">成績参照</a></li>
				<li><a href="${pageContext.request.contextPath}/main/subject" >科目管理</a></li>
				    </ul>
		</nav>
    </c:when>

</c:choose>