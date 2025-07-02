<%--
  ファイル名：STDM002.jsp
  機能概要：学生登録画面
--%>

<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/BASE001.jsp">
    <c:param name="head">
        <%-- このJSP専用のスタイルシートがあればここに記述 --%>
        <%-- <link rel="stylesheet" href="styles.css"> --%>
    </c:param>
    <c:param name="body">
        <div class="login-container">
            <h2>学生情報登録</h2>

            <%-- ★★★ 修正箇所①: エラーメッセージ表示エリア ★★★ --%>
            <%-- コントローラーから渡されたerrorsマップにエラーが1件でもあれば表示 --%>
            <c:if test="${not empty errors}">
                 <div style="color: red; background-color: #ffebee; border: 1px solid #e57373; padding: 10px; border-radius: 5px; margin-bottom: 20px;">
                    <strong>入力内容にエラーがあります:</strong>
                    <ul>
                        <%-- errorsマップの各エラーメッセージをリストで表示 --%>
                        <c:forEach var="entry" items="${errors}">
                            <li><c:out value="${entry.value}" /></li>
                        </c:forEach>
                    </ul>
                 </div>
            </c:if>
            <%-- 汎用のerrorキーにも対応 --%>
            <c:if test="${not empty error}">
                 <div style="color: red; background-color: #ffebee; border: 1px solid #e57373; padding: 10px; border-radius: 5px; margin-bottom: 20px;">
                      <c:out value="${error}" />
                 </div>
            </c:if>

            <form action="StudentCreateExcute" method="post">
                 <%-- 入学年度 --%>
                 <div class="input-group">
                      <label for="ent_year">入学年度<br></label>
                      <select name="ent_year" id="ent_year" required>
                            <option value="0">---------</option> <%-- valueを0に修正 --%>
                            <c:forEach var="year" items="${ent_year_list}">
                                   <%-- ★★★ 修正箇所②: 入力値保持のロジックを修正 ★★★ --%>
                                   <option value="${year}" <c:if test="${year eq ent_year}">selected</c:if>>
                                         <c:out value="${year}" />
                                   </option>
                            </c:forEach>
                      </select>
                 </div>

                 <%-- 学生番号 --%>
                 <div class="input-group">
                      <label for="no">学生番号<br></label>
                      <%-- ★★★ 修正箇所③: value属性のクォートを修正 ★★★ --%>
                      <input type="text" name="no" id="no" placeholder="学生番号を入力してください" value="<c:out value='${no}'/>" required maxlength="10">
                 </div>

                 <%-- 氏名 --%>
                 <div class="input-group">
                      <label for="name">氏名<br></label>
                      <input type="text" name="name" id="name" placeholder="氏名を入力してください" value="<c:out value='${name}'/>" required maxlength="30">
                 </div>

                 <%-- クラス --%>
                 <div class="input-group">
                      <label for="class_num">クラス<br></label>
                      <select name="class_num" id="class_num" required>
                           <c:choose>
                                <c:when test="${not empty class_num_list}">
                                      <%-- ★★★ 修正箇所④: 最初の選択肢のvalueを空に ★★★ --%>
                                      <option value="">クラスを選択してください</option>
                                      <c:forEach var="classVal" items="${class_num_list}">
                                            <option value="${classVal}" <c:if test="${classVal eq class_num}">selected</c:if>>
                                                   <c:out value="${classVal}" />
                                            </option>
                                      </c:forEach>
                                </c:when>
                                <c:otherwise>
                                      <option value="">登録可能なクラスがありません</option>
                                </c:otherwise>
                           </c:choose>
                      </select>
                 </div>

                 <button type="submit" class="login-button">登録して終了</button>
                 <div class="login-link">
                     <a href="STDM001">戻る</a> <%-- 戻るリンク先を一覧画面に修正 --%>
                 </div>
            </form>
        </div>
    </c:param>
</c:import>