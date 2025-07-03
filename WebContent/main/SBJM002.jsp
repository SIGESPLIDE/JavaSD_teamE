<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- a_suzuki --%>

<c:import url="/BASE001.jsp">
    <c:param name="head">
        <link rel="stylesheet" href="styles.css">
    </c:param>
    <c:param name="body">
        <section class="content-form">
            <h2>科目情報登録</h2>

            <%-- action属性をサーブレットのURLパターンに合わせる --%>
            <form action="${pageContext.request.contextPath}/main/SubjectCreateExcute" method="post">
                <div class="input-group">
                    <label for="cd-input">科目コード</label>
                    <%-- 失敗時に値が戻るようにvalue属性を設定 --%>
                    <input type="text" id="cd-input" name="cd" value="<c:out value='${cd}'/>" maxlength="3" required placeholder="科目コードを入力してください">
                    <c:if test="${not empty error}">
                         <div style="color: #fd7e14; margin-bottom: 0.875em;">
                    <p><c:out value="${error}"/></p>
                </div>
            </c:if>
                </div>

                <div class="input-group">
                    <label for="name-input">科目名</label>
                    <input type="text" id="name-input" name="name" value="<c:out value='${name}'/>" maxlength="20" required placeholder="科目名を入力してください">
                </div>

                <div class="form-actions">
                    <input type="submit" value="登録" class="login-button" style="width: auto; padding: 10px 10px;">
                    <%-- 戻るリンクはメニューや一覧画面のサーブレットURLへ --%>
                    <a href="SBJM001.jsp">戻る</a> <%-- 仮のメニュー画面URL --%>
                </div>
            </form>
        </section>
    </c:param>
</c:import>
