<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/BASE001.jsp">
	<c:param name="header">
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/main/css/style-login.css">
	</c:param>
	<c:param name="body">
		<div class="login-page-wrapper"> <%-- 全体の中央寄せのため、BASE001.jspのbodyに直接スタイルが適用できない場合のラッパー --%>
			<div class="login-container">
				<%-- 画像のヘッダー部分 --%>
				<div class="login-header">
					<h2 class="login-title">ログイン</h2>
				</div>

				<%-- フォーム本体 --%>
				<form action="${pageContext.request.contextPath}/login-execute.action" method="post" class="login-form-body">
					<c:if test="${not empty errorMessage}">
						<div class="error-message">・${errorMessage}</div>
					</c:if>

					<div class="form-group">
						<input type="text" id="id" name="id" maxlength="20" required 
							pattern="[A-Za-z0-9]+" placeholder=" " value="<c:out value="${id}"/>" /> <%-- placeholderは空に --%>
						<label for="id">I D</label>
					</div>
					<div class="form-group">
						<input type="password" id="password" name="password" maxlength="20" required 
							pattern="[A-Za-z0-9]+" placeholder=" " /> <%-- placeholderは空に --%>
						<label for="password">パスワード</label>
					</div>

					<div class="form-group checkbox-group"> <%-- チェックボックス用の追加クラス --%>
						<input type="checkbox" id="chk_d_ps" onclick="togglePassword()" />
						<label for="chk_d_ps">パスワードを表示</label>
					</div>

					<div class="form-group submit-group">
						<input type="submit" class="login-button" id="login" value="ログイン" />
					</div>
				</form>
			</div>
		</div> <%-- /login-page-wrapper --%>

		<script>
			function togglePassword() {
				const passwordInput = document.getElementById("password");
				passwordInput.type = document.getElementById("chk_d_ps").checked ? "text"
						: "password";
			}
		</script>
	</c:param>
</c:import>