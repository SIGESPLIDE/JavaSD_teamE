<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 作成者: のはら -->
<c:import url="/BASE001.jsp">
	<c:param name="head">
		<title>学生情報変更</title>
	</c:param>

	<c:param name="body">
		<h2>学生情報変更</h2>

		<!-- 共通エラーメッセージ -->
		<c:if test="${not empty errorMessage}">
			<p class="error">${errorMessage}</p>
		</c:if>

		<form method="post" action="studentUpdateExecute">

			<!-- 表示のみの情報 -->
			<div class="info-box">
				<p><label>入学年度</label><br>
				 ${student.entYear}</p>

				<p><label>学生番号</label><br>
				 ${student.no}</p>
			</div>

			<input type="hidden" name="ent_year" value="${student.entYear}">
			<input type="hidden" name="no" value="${student.no}">

			<!-- 氏名 -->
			<div class="form-group">
				<label>氏名</label><br>
				<c:if test="${not empty nameError}">
					<p class="error" style="color: red; margin: 0;">${nameError}</p>
				</c:if>
				<input type="text" name="name" value="${student.name}" maxlength="30">
			</div>

			<!-- クラス -->
			<div class="form-group">
				<label>クラス</label><br>
				<c:if test="${not empty classError}">
					<p class="error" style="color: red; margin: 0;">${classError}</p>
				</c:if>
				<select name="class_num">
					<option value="">--</option>
					<c:forEach var="cls" items="${classList}">
						<option value="${cls}" ${param.class_num== cls ? 'selected' : (student.classNum == cls ? 'selected' : '')}>${cls}</option>
					</c:forEach>
				</select>
			</div>

			<!-- 在学中 -->
			<div class="checkbox-group">
				<label> 在学中
					<input type="checkbox" name="is_attend" value="true"
						<c:if test="${student.attend}">checked</c:if> >
				</label>
			</div>

			<!-- 学校コード -->
			<input type="hidden" name="school_cd" value="${student.school.cd}">

			<!-- ボタン -->
			<div class="button-group">
				<button type="submit">変更</button><br>
				<a href="${pageContext.request.contextPath}/main/STDM001">戻る</a>
			</div>
		</form>
	</c:param>
</c:import>
