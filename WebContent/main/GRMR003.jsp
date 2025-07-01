<<<<<<< Upstream, based on branch 'master' of https://github.com/SIGESPLIDE/JavaSD_teamE.git
<%@page contentType="text/html; charset=UTF-8"%>Add commentMore actions
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="search-results">
	<%-- 学生名と学生番号の表示 --%>
	<div class="mb-3">
		<span class="fw-bold">氏名：${student.name}(${student.no})</span>
	</div>

	<c:choose>
		<%-- 学生の成績情報が存在する場合、一覧を表示 --%>
		<c:when test="${not empty ExamListStudent}">
			<%-- table-borderlessでセルの縦横線をすべて消す --%>
			<table class="table table-borderless mb-0"
				style="vertical-align: middle;">
				<thead>
					<%-- ヘッダー行の下にだけ線を引く --%>
					<tr class="border-bottom">
						<th class="text-center py-2" style="width: 15%;">科目名</th>
						<th class="text-center py-2" style="width: 15%;">科目コード</th>
						<th class="text-center py-2" style="width: 20%;">回数</th>
						<th class="text-center py-2" style="width: 30%;">点数</th>
					</tr>
				</thead>
				<tbody>
					<%-- 取り出したデータを繰り返しで表示 --%>
					<c:forEach var="tLS" items="${ExamListStudent}" varStatus="loop">
						<%-- 各データ行の下に線を引く (最後の行は除く) --%>
						<tr <c:if test="${!loop.last}">class="border-bottom"</c:if>>
							<%--科目名と科目コード、回数と点数を表示 --%>
							<td class="text-center py-2">${tLS.subjectName}</td>
							<td class="text-center py-2">${tLS.subjectCd}</td>
							<td class="text-center py-2">${tLS.num}</td>
							<td class="text-center py-2">${tLS.point}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<%-- 学生の成績情報が存在しない場合メッセージを表示 --%>
		<c:otherwise>
			<p>成績情報が存在しませんでした</p>
		</c:otherwise>
	</c:choose>
	Add commentMore actions
</div>
=======
<%@page contentType="text/html; charset=UTF-8"%>Add commentMore actions
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="search-results">
	<%-- 学生名と学生番号の表示 --%>
	<div class="mb-3">
		<span class="fw-bold">氏名：${student.name}(${student.no})</span>
	</div>

	<c:choose>
		<%-- 学生の成績情報が存在する場合、一覧を表示 --%>
		<c:when test="${not empty ExamListStudent}">
			<%-- table-borderlessでセルの縦横線をすべて消す --%>
			<table class="table table-borderless mb-0"
				style="vertical-align: middle;">
				<thead>
					<%-- ヘッダー行の下にだけ線を引く --%>
					<tr class="border-bottom">
						<th class="text-center py-2" style="width: 15%;">科目名</th>
						<th class="text-center py-2" style="width: 15%;">科目コード</th>
						<th class="text-center py-2" style="width: 20%;">回数</th>
						<th class="text-center py-2" style="width: 30%;">点数</th>
					</tr>
				</thead>
				<tbody>
					<%-- 取り出したデータを繰り返しで表示 --%>
					<c:forEach var="tLS" items="${ExamListStudent}" varStatus="loop">
						<%-- 各データ行の下に線を引く (最後の行は除く) --%>
						<tr <c:if test="${!loop.last}">class="border-bottom"</c:if>>
							<%--科目名と科目コード、回数と点数を表示 --%>
							<td class="text-center py-2">${tLS.subjectName}</td>
							<td class="text-center py-2">${tLS.subjectCd}</td>
							<td class="text-center py-2">${tLS.num}</td>
							<td class="text-center py-2">${tLS.point}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<%-- 学生の成績情報が存在しない場合メッセージを表示 --%>
		<c:otherwise>
			<p>成績情報が存在しませんでした</p>
		</c:otherwise>
	</c:choose>
</div>Add comment
>>>>>>> 027cae1 restore ExamList 
