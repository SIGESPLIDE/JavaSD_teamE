<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  このJSPファイルの文字コードをUTF-8に設定します。
  また、JSTL（JSP Standard Tag Library）のCoreタグを使用できるようにします。
  JSTLを使うことで、if文や繰り返しなどをJSP上で簡単に記述することができます。
--%>

<c:import url="/base.jsp">
    <%--
      共通のレイアウトファイル（base.jsp）を読み込みます。
      base.jspには、ヘッダーやフッターなど全ページ共通のHTML構造が定義されています。
      この<c:import>では、base.jspにパラメータ（タイトルや本文）を渡して、
      ページごとに表示内容を変えています。
    --%>

    <c:param name="title">アカウント作成</c:param>
    <%--
      ページタイトルとして「アカウント作成」を指定します。
      base.jsp 内で ${param.title} のように参照されます。
    --%>

    <c:param name="body">
        <%--
          base.jsp 内で挿入される「本文部分」の内容をここで指定します。
          ここには、ユーザー登録画面の見た目や入力フォームを記述しています。
        --%>

        <h2>アカウント作成</h2>

        <%-- ユーザー情報を送信するためのフォームを作成します。 --%>
        <form action="register" method="post">
            <%-- ユーザー名の入力欄（必須） --%>
            <p>ユーザー名：<input type="text" name="username" required></p>

            <%-- パスワードの入力欄（必須） --%>
            <p>パスワード：<input type="password" name="password" required></p>

            <%-- 登録ボタン（クリックするとデータが送信されます） --%>
            <p><input type="submit" value="登録"></p>
        </form>

        <%--
          サーバー側でエラーメッセージ（例えば「すでにユーザー名が使われている」など）が
          設定された場合に、その内容を赤文字で表示します。
        --%>
        <c:if test="${not empty error}">
            <p style="color: red;">${error}</p>
        </c:if>

    </c:param>
</c:import>
