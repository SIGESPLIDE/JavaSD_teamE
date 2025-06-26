<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/BASE001.jsp">
    <c:param name="head">
        <link rel="stylesheet" href="styles.css">
    </c:param>
    <c:param name="body">
        <div class="login-container">
            <h2>科目情報登録完了</h2>

            <%-- セッションから完了メッセージを取得して表示し、その後削除する --%>
            <div class="logout-message" style="text-align: center; margin-bottom: 30px;">
                <c:if test="${not empty sessionScope.flash_message}">
                    <c:out value="${sessionScope.flash_message}" />
                    <c:remove var="flash_message" scope="session" />
                </c:if>
                <c:if test="${empty sessionScope.flash_message}">
                    登録が完了しました。
                </c:if>
            </div>

            <%-- リンク先をサーブレットのURLに修正 --%>
            <a href="SubjectCreate">続けて科目を登録する</a>
            <a href="SubjectListController" style="margin-left: 20px;">科目一覧へ</a> <%-- 仮の科目一覧画面URL --%>
        </div>
    </c:param>
</c:import>