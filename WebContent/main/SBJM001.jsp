<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 作成者のはら -->

<c:import url="/BASE001.jsp">
	<c:param name="head">
		<title>科目管理</title>
	</c:param>
	<c:param name="body">
		<h2>科目管理</h2>

		<div class="add-new-link-container">
			<a href="${pageContext.request.contextPath}/main/SBJM002.jsp">新規登録</a>
		</div>

		<div class="table-responsive"></div>
		<table class="table table-hover">
			<thead>
				<tr>
					<th class="text-left">科目コード</th>
					<th class="text-left">科目名</th>
					<th class="text-center"></th>
					<th class="text-center"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="subject" items="${subjects}">
					<tr>
						<td class="text-left">${subject.cd}</td>
						<td class="text-center">${subject.name}</td>
						<td class="text-center"><a
							href="subjectUpdate?cd=${subject.cd}">変更</a></td>
						<td class="text-center"><a
							href="${pageContext.request.contextPath}/main/subjectDelete?cd=${subject.cd}"
							onclick="return confirm('本当に削除しますか？');">削除</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</c:param>
</c:import>
