<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
	<c:when test="${not empty user}">
		<header class="header">
			<div class="header-content">
				<h1>得点管理システム</h1>
				<%-- ログインしている場合（セッションにユーザー情報がある） --%>
				<%-- セッション内のユーザーオブジェクト（例：Teacher型）から name を表示 --%>

				<div class="user-info">
					<span>${user.name}様</span> <a
						href="${pageContext.request.contextPath}/main/logout">ログアウト</a>
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
