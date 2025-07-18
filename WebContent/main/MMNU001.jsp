<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:import url="/BASE001.jsp">
	<c:param name="title">
		<title>メインメニュー</title>
	</c:param>
	<c:param name="body">
		<div class="menu-group">
			<h2>メニュー</h2>
		</div>

		<div class="row justify-content-center">
			<div class="col-12 col-sm-6 col-md-4 col-lg-3 mb-4">
				<div
					class="card text-black bg-student position-relative menu-box equal-height-box">
					<div class="card-body">
						<p class="card-title h5 mb-0">
							<a href="${pageContext.request.contextPath}/main/STDM001">学生管理</a>
						</p>
					</div>
				</div>
			</div>

			<div class="col-12 col-sm-6 col-md-4 col-lg-3 mb-4">
				<div
					class="card text-black bg-score position-relative menu-box equal-height-box">
					<div class="card-body">
						<label class="card-title h5 mb-0">成績管理</label>
						<p class="card-title h5 mb-0">
							<a href="${pageContext.request.contextPath}/main/ExamRegist">
								成績登録 </a>
						</p>
						<p class="card-title h5 mb-0">
							<a href="${pageContext.request.contextPath}/main/ExamList">
								成績参照 </a>
						</p>
					</div>
				</div>
			</div>

			<div class="col-12 col-sm-6 col-md-4 col-lg-3 mb-4">
				<div
					class="card text-black bg-subject position-relative menu-box equal-height-box">
					<div class="card-body">
						<p class="card-title h5 mb-0">
							<a href="${pageContext.request.contextPath}/main/subject">科目管理</a>
						</p>
					</div>
				</div>
			</div>
		</div>

	</c:param>
</c:import>
