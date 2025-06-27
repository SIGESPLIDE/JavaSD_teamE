<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/BASE001.jsp">
	<c:param name="body">
		<%-- サーバーサイドで発生したエラーメッセージを表示する領域 --%>
		<c:if test="${not empty error}">
			<%-- CSSでエラーメッセージ用のスタイルを定義するとより良い (例: .error-message) --%>
			<div
				style="color: red; background-color: #ffebee; border: 1px solid #e57373; padding: 10px; border-radius: 5px; margin-bottom: 20px;">
				<c:out value="${errorMessage}" />
			</div>
		</c:if>
	</c:param>
</c:import>