<%--
  ファイル名：STDM003.jsp
  機能概要：学生登録完了画面
--%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/BASE001.jsp">
	<c:param name="head">
	    <link rel="stylesheet" href="styles.css">
	</c:param>
	<c:param name="body">

        <%-- 登録画面と同じコンテナクラスを使い、レイアウトを統一 --%>
        <div class="login-container">
             <%-- 画面タイトル --%>
             <h2>学生情報登録</h2>

             <%-- 登録完了メッセージ --%>
             <div class="logout-message" style="text-align: center; margin-bottom: 30px;">
                  登録が完了しました
             </div>

             <%-- 戻るリンク --%>
             <a href="STDM002.jsp">戻る</a>

             <%-- 学生一覧リンク --%>
             <a href="STDM001.jsp" style="margin-left: 20px;">学生一覧</a>
        </div>

    </c:param>
</c:import>