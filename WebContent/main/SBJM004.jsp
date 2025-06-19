<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 作成者のはら -->
<c:import url="/BASE001.jsp">
  <c:param name="head">
    <title>科目管理</title>
  </c:param>
  <c:param name="body">
    <h2>科目情報変更</h2>

    <!-- エラーメッセージ -->
    <c:if test="${not empty errorMessage}">
      <p style="color: red;">${errorMessage}</p>
    </c:if>

    <form method="post" action="subjectUpdateExecute">
      <div class="form-group">
        <label for="subjectCode">科目コード</label><br>
        <input type="text" id="subjectCode" name="subjectCode" value="${subject.cd}" readonly>
      </div>
      <br>
      <div class="form-group">
        <label for="subjectName">科目名</label><br>
        <input type="text" id="subjectName" name="subjectName" value="${subject.name}">
      </div>
      <br>
      <!-- hiddenでschoolCdも送る（必要な場合） -->
      <input type="hidden" name="SCHOOL_CD" value="${subject.school.cd}">
      <div class="button-group">
        <button type="submit">変更</button>
        <a href="subjectList">戻る</a>
      </div>
    </form>
  </c:param>
</c:import>
