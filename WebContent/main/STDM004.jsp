<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 作成者: のはら -->
<c:import url="/BASE001.jsp">
  <c:param name="head">
    <title>学生情報変更</title>
  </c:param>
  <c:param name="body">
    <h2>学生情報変更</h2>

    <!-- エラーメッセージ表示 -->
    <c:if test="${not empty errorMessage}">
      <p style="color:red;">${errorMessage}</p>
    </c:if>

    <form method="post" action="studentUpdateExecute">

      <!-- 入学年度（表示のみ） -->
      <div class="form-group">
        <label>入学年度</label><br>
        <input type="text" value="${student.entYear}" readonly>
      </div>
      <br>

      <!-- 学生番号（表示と送信用） -->
      <div class="form-group">
        <label>学生番号</label><br>
        <input type="text" value="${student.no}" readonly>
        <input type="hidden" name="no" value="${student.no}">
      </div>
      <br>

      <!-- 氏名 -->
      <div class="form-group">
        <label>氏名</label><br>
        <input type="text" name="name" value="${student.name}" maxlength="30">
      </div>
      <br>

      <!-- クラス選択 -->
      <div class="form-group">
        <label>クラス</label>
            <select name="class_num"> <%-- input type="text" から select に変更 --%>
                <option value="">--</option>
                <c:forEach var="cls" items="${classList}"> <%-- Servletから渡されたclassListを使用 --%>
                    <option value="${cls}" ${param.classId == cls ? 'selected' : ''}>${cls}</option>
                </c:forEach>
            </select>

      <!-- 在学中チェック -->
      <div class="form-group">
        <label>在学中</label><br>
        <input type="checkbox" name="is_attend" value="true" <c:if test="${student.attend}">checked</c:if>>
      </div>
      <br>

      <!-- ボタン -->
      <div class="button-group">
        <button type="submit">変更</button>
        <a href="studentList">戻る</a>
      </div>

    </form>
  </c:param>
</c:import>
