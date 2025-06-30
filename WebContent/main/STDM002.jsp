<%--
  ファイル名：STDM002.jsp
  機能概要：学生登録画面
--%>

<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/BASE001.jsp">
	<c:param name="head">
	    <link rel="stylesheet" href="styles.css">
	</c:param>
	<c:param name="body">
        <%-- フォーム全体を login-container で囲む --%>
        <div class="login-container">
            <%-- 画面タイトル --%>
            <h2>学生情報登録</h2>

            <%-- サーバーサイドで発生したエラーメッセージを表示する領域 --%>
            <c:if test="${not empty error}">
                 <%-- CSSでエラーメッセージ用のスタイルを定義するとより良い (例: .error-message) --%>
                 <div style="color: red; background-color: #ffebee; border: 1px solid #e57373; padding: 10px; border-radius: 5px; margin-bottom: 20px;">
                      <c:out value="${error}" />
                 </div>
            </c:if>

            <%-- 学生情報登録フォーム --%>
            <form action="StudentCreateExcute" method="post">
                 <%-- 各項目を input-group で囲む --%>
                 <div class="input-group">
                      <label for="ent_year">入学年度<br></label>
                      <select name="ent_year" id="ent_year" required>
                            <option value="">---------</option>
                            <c:forEach var="year" items="${ent_year_list}">
                                   <option value="${year}" <c:if test="${year eq ent_year}">selected</c:if>>
                                         <c:out value="${year}" />
                                   </option>
                            </c:forEach>
                      </select>
                 </div>

                 <div class="input-group">
                      <label for="no">学生番号<br></label>
                      <input type="text" name="no" id="no" placeholder="学生番号を入力してください" value="<c:out value='${no}'/>" required maxlength="10">
                 </div>

                 <div class="input-group">
                      <label for="name">氏名<br></label>
                      <input type="text" name="name" id="name" placeholder="氏名を入力してください" value="<c:out value='${name}'/>" required maxlength="30">
                 </div>

                 <div class="input-group">
                      <label for="class_num">クラス<br></label>
                      <select name="class_num" id="class_num" required>
                           <c:choose>
                                <%-- class_num_list が空でない場合 --%>
                                <c:when test="${not empty class_num_list}">
                                      <option value="">クラスを選択してください</option>
                                      <c:forEach var="classVal" items="${class_num_list}">
                                            <option value="${classVal}" <c:if test="${classVal eq class_num}">selected</c:if>>
                                                   <c:out value="${classVal}" />
                                            </option>
                                      </c:forEach>
                                </c:when>
                                <%-- class_num_list が空の場合 --%>
                                <c:otherwise>
                                      <option value="">登録可能なクラスがありません</option>
                                </c:otherwise>
                           </c:choose>
                      </select>
                 </div>

                 <%-- ボタンに login-button クラスを適用 --%>
                 <button type="submit" class="login-button">登録して終了</button>

                 <%-- 戻るリンクもスタイルを整える --%>
                 <div class="login-link">
                     <a href="STDM001.jsp">戻る</a>
                 </div>
            </form>
        </div>
    </c:param>
</c:import>
