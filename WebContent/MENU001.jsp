<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<a href="${pageContext.request.contextPath}/#"></a>
<a href="${pageContext.request.contextPath}/#"></a>


<c:choose>

    <%-- ログインしている場合（セッションにユーザー情報がある） --%>
    <c:when test="${not empty session_user}">
        <%-- セッション内のユーザーオブジェクト（例：User型）から username を表示 --%>
        <span>ようこそ、${session_user.username} さん</span>
        <a href="${pageContext.request.contextPath}/accounts/logout">ログアウト</a>
    </c:when>

    <%-- 未ログインの場合（セッションにユーザー情報がない） --%>
    <c:otherwise>
<nav class="side-menu">
    <ul>
        <li><a href="#">学生管理</a></li>
        <li><a href="#">成績管理</a></li>
        <li><a href="#">成績登録</a></li>
        <li><a href="#">成績参照</a></li>
        <li><a href="#">科目管理</a></li>
    </ul>
</nav>
    </c:otherwise>


</c:choose>

<hr>
