<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/BASE001.jsp">
	<c:param name="head">
	</c:param>
	<c:param name="body">
		<div class="login-container">
        <form action="${pageContext.request.contextPath}/login-execute.action" method="post">
				<h2>ログイン</h2>

    <c:if test="${not empty errorMessage}">
        <div class="error-message">
            ・${errorMessage}
        </div>
    </c:if>

    <div class="form-group">
        <label for="id">ID</label>
        <input type="text" id="id" name="id"
            maxlength="20" required pattern="[A-Za-z0-9]+"
            placeholder="半角でご入力ください" value="<c:out value="${id}"/>" />
    </div>
				<div class="form-group">
					<label for="password">パスワード</label> <input type="password"
						id="password" name="password" maxlength="20" required
						pattern="[A-Za-z0-9]+" placeholder="20文字以内の半角英数字でご入力ください" />
				</div>

				<div class="form-group">
					<input type="checkbox" id="chk_d_ps" onclick="togglePassword()" />
					<label for="chk_d_ps">パスワードを表示</label>
				</div>

				<div class="form-group">
					<input type="submit" class="login-button" id="login" value="ログイン" />
				</div>
			</form>
		</div>

		<script>
			function togglePassword() {
				const passwordInput = document.getElementById("password");
				passwordInput.type = document.getElementById("chk_d_ps").checked ? "text"
						: "password";
			}
		</script>
	</c:param>
</c:import>