<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/BASE001.jsp">
	<c:param name="head">
		<title>学生情報変更</title>
	</c:param>

	<c:param name="body">
		<h2>学生情報変更</h2>

		<c:if test="${not empty errors}">
			<div style="color: red; background-color: #ffebee; border: 1px solid #e57373; padding: 10px; border-radius: 5px; margin-bottom: 20px;">
				<strong>入力内容にエラーがあります:</strong>
				<ul>
					<c:forEach var="entry" items="${errors}">
						<li><c:out value="${entry.value}" /></li>
					</c:forEach>
				</ul>
			</div>
		</c:if>

		<c:if test="${not empty error}">
			<div style="color: red; background-color: #ffebee; border: 1px solid #e57373; padding: 10px; border-radius: 5px; margin-bottom: 20px;">
				${error}
			</div>
		</c:if>

		<form method="post" action="studentUpdateExecute">
			<div class="info-box">
				<p><label>入学年度</label><br> ${student.entYear}</p>
				<p><label>学生番号</label><br> ${student.no}</p>
			</div>

			<input type="hidden" name="ent_year" value="${student.entYear}">
			<input type="hidden" name="no" value="${student.no}">

			<div class="form-group">
				<label>氏名</label><br>
				<c:if test="${not empty errors['name']}">
					<p class="error" style="color:red; margin:0">${errors['name']}</p>
				</c:if>
				<input type="text" name="name" value="${student.name}" maxlength="30">
			</div>

			<div class="form-group">
				<label>クラス</label><br>
				<c:if test="${not empty errors['class_num']}">
					<p class="error" style="color:red; margin:0">${errors['class_num']}</p>
				</c:if>
				<select name="class_num">
					<option value="">--</option>
					<c:forEach var="cls" items="${classList}">
						<option value="${cls}" ${cls == student.classNum ? 'selected' : ''}>${cls}</option>
					</c:forEach>
				</select>
			</div>

			<div class="checkbox-group">
				<label>在学中
					<input type="checkbox" name="is_attend" value="true"
						<c:if test="${student.attend}">checked</c:if>>
				</label>
			</div>

			<input type="hidden" name="school_cd" value="${student.school.cd}">

			<div class="button-group">
				<button type="submit">変更</button><br>
				<a href="${pageContext.request.contextPath}/main/STDM001">戻る</a>
			</div>
		</form>
	</c:param>
</c:import>
