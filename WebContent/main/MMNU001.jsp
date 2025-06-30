<%@page contentType="text/html; charset=UTF-8"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:import url="/BASE001.jsp">
	<c:param name="title">
		<title>メインメニュー</title>
	</c:param>
	<c:param name="body">
		<div class="row justify-content-center">
			<div class="col-12 col-sm-6 col-md-4 col-lg-3 mb-4">
				<div
					class="card text-black bg-student position-relative menu-box equal-height-box">
					<div class="card-body">
						<h3 class="card-title h5 mb-0">学生管理</h3>
						<a href="${pageContext.request.contextPath}/main/STDM001"></a>
					</div>
				</div>
			</div>

			<div class="col-12 col-sm-6 col-md-4 col-lg-3 mb-4">
				<div
					class="card text-black bg-score position-relative menu-box equal-height-box">
					<div class="card-body">
						<p class="card-title h5 mb-0">
							成績管理
						</p>
						<p class="card-title h5 mb-0">
							<a href="${pageContext.request.contextPath}/main/STDM001">
								成績登録
							</a>
						</p>
						<p class="card-title h5 mb-0">
							<a href="${pageContext.request.contextPath}/main/STDM001">
								成績参照
							</a>
						</p>
					</div>
				</div>
			</div>

			<div class="col-12 col-sm-6 col-md-4 col-lg-3 mb-4">
				<div
					class="card text-black bg-subject position-relative menu-box equal-height-box">
					<div class="card-body">
						<a href="${pageContext.request.contextPath}/main/subject">科目管理</a>
					</div>
				</div>
			</div>
		</div>

		<hr>

	</c:param>
</c:import>
