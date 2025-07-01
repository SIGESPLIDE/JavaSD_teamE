<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
    ファイル名: GRMU002.jsp
    機能: 成績登録完了画面
    説明: base.jspをテンプレートとして使用し、メインコンテンツ部分を定義します。
--%>

<c:import url="/BASE001.jsp">
    <%-- テンプレート(base.jsp)に渡すパラメータを設定 --%>
    <c:param name="title">
        <title>得点管理システム - 成績登録完了</title>
    </c:param>

    <%-- メインコンテンツ部分を定義 --%>
    <c:param name="body">
        <div class="container-fluid">
            <h2 class="mb-4">成績管理</h2>

            <%-- 登録完了メッセージ --%>
            <div class="alert alert-success" role="alert">
                登録が完了しました
            </div>

            <div class="mt-4">
                <%-- 画面項目定義書に基づきリンクを設置 --%>
                <%-- 「戻る」は成績登録画面へ --%>
                <a href="ExamRegist" class="btn btn-secondary">戻る</a>

                <%-- 「成績参照」は成績参照画面へ (URLは仮) --%>
                <a href="ExamList" class="btn btn-link">成績参照</a>
            </div>
        </div>
    </c:param>
</c:import>