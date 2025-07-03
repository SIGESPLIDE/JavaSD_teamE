<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/BASE001.jsp" >
<c:param name="header">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/main/css/style-list.css">
</c:param>
<c:param name="body">
<div class="container mt-4">
	<%-- ここを修正 --%>
	<h2 class="mb-4">
		成績参照
		<c:if test="${not empty subject}">
			（科目）
		</c:if>
		<c:if test="${not empty student}">
			（学生）
		</c:if>
	</h2>

	<div class="card custom-card">
		<div class="card-body">

			<form action="${pageContext.request.contextPath}/main/ExamList" method="post">
				<div class="row mb-3 align-items-center">
					<div class="col-auto me-3">
						<strong class="normal-font-weight" style="font-size: 1rem;">科目情報</strong>
					</div>
					<div class="col-md-3">
						<label for="enrollmentYear" class="form-label mb-1">入学年度</label>
						<select class="form-control" id="enrollmentYear" name="f1">
							<option value="">--------</option>
							<c:forEach var="selectEntYear" items="${entYearList}">
								<option value="${selectEntYear}" <c:if test="${selectEntYear == entYear}">selected</c:if>>
									${selectEntYear}
								</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-md-3">
						<label for="class" class="form-label mb-1">クラス</label>
						<select class="form-control" id="class" name="f2">
							<option value="">--------</option>
							<c:forEach var="course" items="${classNumList}">
								<option value="${course.class_num}" <c:if test="${course.class_num == classNum}">selected</c:if>>
									${course.class_num}
								</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-md-3">
						<label for="subject" class="form-label mb-1">科目</label>
						<select class="form-control" id="subject" name="f3">
							<option value="">--------</option>
							<c:forEach var="subject" items="${subjectList}">
								<option value="${subject.cd}" <c:if test="${subject.cd == subjectCd}">selected</c:if>>
									${subject.cd}
								</c:forEach>
						</select>
					</div>

					<div class="col-auto d-flex align-items-center">
						<button type="submit" class="btn btn-gray">検索</button>
					</div>
					<input type="hidden" name="f" value="sj">
				</div>
			</form>

			<hr class="my-4">
			<form action="${pageContext.request.contextPath}/main/ExamListStudent" method="post">
				<div class="row mb-3 align-items-center">
					<div class="col-auto me-3">
						<strong class="normal-font-weight" style="font-size: 1rem;">学生情報</strong>
					</div>
					<div class="col-md-6">
						<label for="studentId" class="form-label mb-1">学生番号</label>
						<input type="text" class="form-control" id="studentId" name="f4"
							placeholder="学生番号を入力してください">
					</div>
					<div class="col-auto d-flex align-items-center">
						<button type="submit" class="btn btn-gray">検索</button>
					</div>
					<input type="hidden" name="f" value="st">
				</div>
			</form>

			<c:if test="${not empty sjError}">
				<div class="alert alert-warning">${sjError}</div>
			</c:if>

		</div>
	</div>

	<c:if test="${not empty error_student}">
		<div class="alert alert-danger mt-3">${error_student}</div>
	</c:if>

	<c:if test="${not empty subject}">
		<c:import url="/main/GRMR002.jsp" />
	</c:if>

	<c:if test="${not empty student}">
		<c:import url="/main/GRMR003.jsp" />
	</c:if>

	<c:if test="${empty subject && empty student && empty error_student}">
		<div class="mt-3 alert-yellow">毎項目を選択または学生情報を入力して検索ボタンをクリックしてください</div>
	</c:if>
</div>
</c:param>
</c:import>