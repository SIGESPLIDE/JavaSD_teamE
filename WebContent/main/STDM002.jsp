<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%--
  ファイル名：STDM002.jsp
  機能概要：学生登録画面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 画面タイトル --%>
<h2>学生情報登録</h2>

<%-- サーバーサイドで発生したエラーメッセージを表示する領域 --%>
<%-- Actionクラスで request.setAttribute("error", "エラーメッセージ") されている場合に表示 --%>
<c:if test="${not empty error}">
    <div style="color: red; margin-bottom: 15px;">
        <c:out value="${error}" />
    </div>
</c:if>

<%-- 学生情報登録フォーム --%>
<form action="StudentCreateExecute.action" method="post">
    <%-- 入学年度 --%>
    <div>
        <label for="ent_year">入学年度</label>
        <div>
            <select name="ent_year" id="ent_year" required>
                <option value="">---------</option>
                <%-- Actionクラスから渡された ent_year_list (年のリスト) をループで表示 --%>
                <c:forEach var="year" items="${ent_year_list}">
                    <%-- エラーで再表示された際に、入力されていた値を選択状態にする --%>
                    <option value="${year}" <c:if test="${year eq ent_year}">selected</c:if>>
                        <c:out value="${year}" />
                    </option>
                </c:forEach>
            </select>
        </div>
    </div>

    <%-- 学生番号 --%>
    <div style="margin-top: 15px;">
        <label for="no">学生番号</label>
        <div>
            <%-- エラーで再表示された際に、入力されていた値を value 属性で復元 --%>
            <input type="text" name="no" id="no" placeholder="学生番号を入力してください" value="<c:out value='${no}'/>" required maxlength="10">
        </div>
    </div>

    <%-- 氏名 --%>
    <div style="margin-top: 15px;">
        <label for="name">氏名</label>
        <div>
            <%-- エラーで再表示された際に、入力されていた値を value 属性で復元 --%>
            <input type="text" name="name" id="name" placeholder="氏名を入力してください" value="<c:out value='${name}'/>" required maxlength="30">
        </div>
    </div>

    <%-- クラス --%>
    <div style="margin-top: 15px;">
        <label for="class_num">クラス</label>
        <div>
            <select name="class_num" id="class_num" required>
                 <%-- Actionクラスから渡された class_num_list (クラス番号のリスト) をループで表示 --%>
                <c:forEach var="classVal" items="${class_num_list}">
                     <%-- エラーで再表示された際に、入力されていた値を選択状態にする --%>
                    <option value="${classVal}" <c:if test="${classVal eq class_num}">selected</c:if>>
                        <c:out value="${classVal}" />
                    </option>
                </c:forEach>
            </select>
        </div>
    </div>

    <%-- ボタンとリンク --%>
    <div style="margin-top: 20px;">
        <button type="submit">登録して終了</button>
        <%-- 「戻る」は学生管理一覧画面へ遷移する想定 --%>
        <a href="StudentList.action" style="margin-left: 10px;">戻る</a>
    </div>
</form>
</body>
</html>