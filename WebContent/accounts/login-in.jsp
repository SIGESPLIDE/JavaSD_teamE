<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/base.jsp">

<c:param name="title">ログイン</c:param>

<c:param name="body">

<form action="login" method="post">
	<p>ログイン名<input type="text" name="login" value="${ login }"></p>
	<p>パスワード<input type="password" name="password"></p>
	<input type="submit" value="ログイン">
	<p>${ errorMessage }</p>
</form>

</c:param>

</c:import>