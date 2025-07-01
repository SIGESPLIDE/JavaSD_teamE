<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:import url="/BASE001.jsp">
	<c:param name="head">
		<meta charset="UTF-8">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<title>科目管理テーブル テスト</title>

		<link rel="stylesheet"
			href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
			integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
			crossorigin="anonymous">

		
	</c:param>
	<c:param name="body">
		<div class="container mt-4">
			<h2>科目管理</h2>

			<div class="add-new-link-container">
				<a href="#">新規登録</a>
			</div>

			<c:set var="subjectsExist" value="true" />
			<%-- テストのために常に科目がある状態に設定 --%>
			<%-- <c:set var="subjectsExist" value="false" /> --%>
			<%-- 科目がない状態をテストする場合はこちらを有効に --%>

			<c:choose>
				<c:when test="${!subjectsExist}">
					<%-- subjectsExistがfalseの場合 --%>
					<div class="alert alert-info" role="alert">科目情報が登録されていません。</div>
				</c:when>
				<c:otherwise>
					<div class="table-responsive">
						<table class="table table-bordered table-hover">
							<thead>
								<tr>
									<th class="text-left">科目コード</th>
									<th class="text-left">科目名</th>
									<th class="text-center"></th>
									<th class="text-center"></th>
								</tr>
							</thead>
							<tbody>
								<%-- テスト用のダミーデータ --%>
								<tr>
									<td class="text-left">A02</td>
									<td>国語</td>
									<td class="text-center"><a href="#">変更</a></td>
									<td class="text-center"><a href="#"
										onclick="return confirm('本当に削除しますか？');">削除</a></td>
								</tr>
								<tr>
									<td class="text-left">B09</td>
									<td>基本情報B</td>
									<td class="text-center"><a href="#">変更</a></td>
									<td class="text-center"><a href="#"
										onclick="return confirm('本当に削除しますか？');">削除</a></td>
								</tr>
								<tr>
									<td class="text-left">C77</td>
									<td>ファミマの店長</td>
									<td class="text-center"><a href="#">変更</a></td>
									<td class="text-center"><a href="#"
										onclick="return confirm('本当に削除しますか？');">削除</a></td>
								</tr>
								<%-- 別のデータを追加して、レイアウトが崩れないか確認できます --%>
								<tr>
									<td class="text-left">D11</td>
									<td>情報セキュリティ概論</td>
									<td class="text-center"><a href="#">変更</a></td>
									<td class="text-center"><a href="#"
										onclick="return confirm('本当に削除しますか？');">削除</a></td>
								</tr>
							</tbody>
						</table>
					</div>
				</c:otherwise>
			</c:choose>
		</div>

		<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
			integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
			crossorigin="anonymous"></script>
		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
			integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
			crossorigin="anonymous"></script>
		<script
			src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
			integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
			crossorigin="anonymous"></script>
	</c:param>
</c:import>