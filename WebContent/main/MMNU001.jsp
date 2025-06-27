<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:import url="/BASE001.jsp">
	<c:param name="title">
		<title>メインメニュー</title>
	</c:param>
	<c:param name="body">
		<div class="title">メニュー</div>

			<div class="row justify-content-center">
		<div class="col-12 col-sm-6 col-md-4 col-lg-3 mb-4">
			<div class="card text-white bg-primary position-relative menu-box equal-height-box">
				<div class="card-body">
					<h3 class="card-title h5 mb-0">成績参照<br>（テスト）</h3> <a href="${pageContext.request.contextPath}/main/STDM001" class="stretched-link"></a>
				</div>
			</div>
		</div>

		<div class="col-12 col-sm-6 col-md-4 col-lg-3 mb-4">
			<div class="card text-white bg-success position-relative menu-box equal-height-box">
				<div class="card-body">
					<h3 class="card-title h5 mb-0">科目管理<br>登録<br>削除</h3> <a href="${pageContext.request.contextPath}/main/STDM001" class="stretched-link"></a>
				</div>
			</div>
		</div>

		<div class="col-12 col-sm-6 col-md-4 col-lg-3 mb-4">
			<div class="card text-white bg-info position-relative menu-box equal-height-box">
				<div class="card-body">
					<h3 class="card-title h5 mb-0">その他メニュー</h3>
					<a href="#" class="stretched-link"></a>
				</div>
			</div>
		</div>
	</div>

		<hr>
		<div class="base-container">
			<div class="container-fluid flex-grow-1">
				       
				<div class="row flex-grow-1">
					           
					<nav class="col-md-3 sidebar mb-3 mb-md-0">
						               
						<ul class="list-unstyled">
							                   
							<li><a
								href="${pageContext.request.contextPath}/main/STDM001">学生管理</a></li>
							                   
							<li><a
								href="${pageContext.request.contextPath}/main/STDM001">成績登録</a></li>
							                   
							<li><a href="#">メニューアイテム 3</a></li>                
						</ul>
						           
					</nav>
					       
				</div>
				   
			</div>
			   
		</div>

	</c:param>
</c:import>