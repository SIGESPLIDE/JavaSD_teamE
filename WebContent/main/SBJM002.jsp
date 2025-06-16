<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>得点管理システム</title>
<%-- 提供されたstyle.cssを読み込みます --%>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">

<%--
  提供されたCSSをベースに、科目登録画面のレイアウト（サイドバー＋コンテンツ）
  を実現するための追加スタイルを定義します。
--%>
<style>
    /* bodyのalign-itemsを上書きして、コンテンツが中央に寄らないようにする */
    body {
        align-items: stretch;
    }

    /* ヘッダーのユーザー情報表示用のスタイル */
    .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 10px 5%; /* 左右に余白を追加 */
    }
    .user-info {
        font-size: 16px;
    }
    .user-info a {
        margin-left: 15px;
    }

    /*
      メインコンテンツのレイアウトを調整。
      中央寄せ(justify-content: center)を解除し、サイドバーとコンテンツが並ぶようにする。
    */
    .main-content {
        justify-content: flex-start;
        align-items: flex-start;
        width: 90%;
        max-width: 1200px;
        margin: 0 auto; /* main-content自体を中央に配置 */
        gap: 20px; /* サイドバーとコンテンツの間の余白 */
    }

    /* サイドバー用のスタイルを新規定義 */
    .sidebar {
        width: 200px;
        flex-shrink: 0;
        background-color: #ffffff;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    }
    .sidebar h3 {
        margin-top: 0;
        margin-bottom: 15px;
        padding-bottom: 10px;
        border-bottom: 1px solid #eee;
        font-size: 18px;
    }
    .sidebar ul {
        list-style: none;
        padding: 0;
        margin: 0;
    }
    .sidebar ul li a {
        display: block;
        padding: 10px;
        color: #333;
        border-radius: 4px;
        text-decoration: none;
    }
    .sidebar ul li a:hover, .sidebar ul li a.active {
        background-color: #f0f2f5;
    }

    /*
      フォームコンテナのスタイル。
      .login-containerをベースにするが、幅を可変にするためmax-widthは設定しない。
    */
    .content-form {
        flex-grow: 1; /* 残りのスペースをすべて使用 */
        background-color: #ffffff;
        padding: 30px 40px;
        border-radius: 8px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        border-top: 5px solid #66b3ff;
    }
    .content-form h2 {
        text-align: left;
    }

    /* エラーメッセージ用のスタイル */
    .error-message {
        color: orange;
        font-size: 14px;
        margin-top: 5px;
    }

    .form-actions {
        margin-top: 30px;
    }
    .form-actions a {
        margin-left: 20px;
    }
</style>
</head>
<body>

    <%-- ヘッダー: .headerクラスを適用 --%>
    <header class="header">
        <h1>得点管理システム</h1>
        <div class="user-info">
            <span>${user.name} 様</span>
            <a href="logout.action">ログアウト</a>
        </div>
    </header>

    <%-- メインコンテンツ: .main-contentクラスを適用 --%>
    <main class="main-content">

        <%-- サイドバー: .sidebarクラスを適用 --%>
        <nav class="sidebar">
            <h3>メニュー</h3>
            <ul>
                <li><a href="#">学生管理</a></li>
                <li><a href="#">成績管理</a></li>
                <li><a href="#">成績登録</a></li>
                <li><a href="#">成績参照</a></li>
                <li><a href="SubjectList.action" class="active">科目管理</a></li>
            </ul>
        </nav>

        <%-- コンテンツ本体: .content-formクラスを適用 --%>
        <section class="content-form">
            <h2>科目情報登録</h2>

            <form action="SubjectCreateExecute.action" method="post">
                <%-- 科目コード: .input-groupクラスを適用 --%>
                <div class="input-group">
                    <label for="cd-input">科目コード</label>
                    <input type="text" id="cd-input" name="cd" value="<c:out value='${cd}'/>" maxlength="3" required placeholder="科目コードを入力してください">
                    <c:if test="${not empty errors.cd}">
                        <p class="error-message">${errors.cd}</p>
                    </c:if>
                </div>

                <%-- 科目名: .input-groupクラスを適用 --%>
                <div class="input-group">
                    <label for="name-input">科目名</label>
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

    <%-- フッター: .footerクラスを適用 --%>
    <footer class="footer">
        <p>© 2023 TIC 大原学園</p>
    </footer>

</body>
</html>