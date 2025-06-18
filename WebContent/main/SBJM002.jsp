<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/BASE001.jsp">
	<c:param name="head">
	    <link rel="stylesheet" href="styles.css">
	</c:param>
	<c:param name="body">
<%--
  ファイル名：SBJM002.jsp
  機能概要：科目登録画面
--%>

        <section class="content-form">
            <h2>科目情報登録</h2>

            <form action="SubjectCreateExecute.action" method="post">
                <%-- 科目コード: .input-groupクラスを適用 --%>
                <div class="input-group">
                    <label for="cd-input">科目コード<br></label>
                    <input type="text" id="cd-input" name="cd" value="<c:out value='${cd}'/>" maxlength="3" required placeholder="科目コードを入力してください">
                    <c:if test="${not empty errors.cd}">
                        <p class="error-message">${errors.cd}</p>
                    </c:if>
                </div>

                <%-- 科目名: .input-groupクラスを適用 --%>
                <div class="input-group">
                    <label for="name-input">科目名<br></label>
                    <input type="text" id="name-input" name="name" value="<c:out value='${name}'/>" maxlength="20" required placeholder="科目名を入力してください">
                    <c:if test="${not empty errors.name}">
                        <p class="error-message">${errors.name}</p>
                    </c:if>
                </div>

                <%-- ボタンとリンク --%>
                <div class="form-actions">
                    <%-- .login-buttonクラスを適用 --%>
                    <input type="submit" value="登録" class="login-button" style="width: auto; padding: 10px 25px;">
                    <a href="SubjectList.action">戻る</a>
                </div>
            </form>
        </section>

    </main>
    </c:param>
</c:import>