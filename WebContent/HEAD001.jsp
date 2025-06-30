<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
	<c:when test="${not empty session_user}">
		<header class="header">
			<div class="header-content">
				<h1>得点管理システム</h1>
				<%-- ログインしている場合（セッションにユーザー情報がある） --%>
				<%-- セッション内のユーザーオブジェクト（例：User型）から username を表示 --%>

				<div class="user-info">
					<span>${session_user.username}様</span> <a
						href="${pageContext.request.contextPath}/main/LOGO001">ログアウト</a>
				</div>
			</div>
		</header>
	</c:when>
	<c:otherwise>
		<header class="header">
			<h1>得点管理システム</h1>
		</header>
	</c:otherwise>
</c:choose>
