<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<<<<<<< HEAD
			<c:if test="${not empty testListSubject}">
				<div class="search-results">
					<div class="mb-3">
						<span class="fw-bold">科目：${subject.name}</span>
					</div>
<form method="post" action="testexecute">Add commentMore actions
						<%-- table-borderlessでセルの縦横線をすべて消す --%>
						<table class="table table-borderless mb-0"
							style="vertical-align: middle;">
							<thead>
								<%-- ヘッダー行の下にだけ線を引く --%>
								<tr class="border-bottom">
									<th class="text-center py-2" style="width: 15%;">入学年度</th>
									<th class="text-center py-2" style="width: 15%;">クラス</th>
									<th class="text-center py-2" style="width: 20%;">学生番号</th>
									<th class="text-center py-2" style="width: 30%;">氏名</th>
									<th class="text-center py-2" style="width: 10%;">1回</th>
									<th class="text-center py-2" style="width: 10%;">2回</th>
								</tr>
							</thead>
							<tbody>
								<%-- 取り出したデータを繰り返しで表示 --%>
								<c:forEach var="tLS" items="${testListSubject}" varStatus="loop">
									<%-- 各データ行の下に線を引く (最後の行は除く) --%>
									<tr <c:if test="${!loop.last}">class="border-bottom"</c:if>>
										<%-- 各セルの文字揃えと上下の余白(padding)を調整 --%>
										<td class="text-center py-2">${tLS.entYear}</td>
										<td class="text-center py-2">${tLS.classNum}</td>
										<td class="text-center py-2">${tLS.studentNo}</td>
										<td class="text-center py-2">${tLS.studentName}</td>
										<td class="text-center py-2">
											<%-- デフォルト値をハイフンに設定 --%>
							                <c:set var="point1" value="-"/>
							                <%-- 2. Mapをループ --%>
							                <c:forEach var="pointEntry" items="${tLS.points}">
							                	<%-- キーが1かどうかチェック --%>
							                    <c:if test="${pointEntry.key == 1}">
							                    	<%-- 一致したら値を上書き --%>
							                        <c:set var="point1" value="${pointEntry.value}"/>
							                    </c:if>
							                </c:forEach>
							                <%-- 最終的な値を表示 --%>
							                ${point1}
							            </td>
							            <%-- 2回目の点数表示 --%>
							            <td class="text-center py-2">
							                <c:set var="point2" value="-"/>
							                <c:forEach var="pointEntry" items="${tLS.points}">
							                    <c:if test="${pointEntry.key == 2}">
							                        <c:set var="point2" value="${pointEntry.value}"/>
							                    </c:if>
							                </c:forEach>
							                ${point2}
							            </td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</form>
				</div>
			</c:if>
			<%-- 情報が存在しない場合メッセージを表示 --%>
			<c:if test="${empty testListSubject}"><p>学生情報が存在しませんでした</p></c:if>
=======
<c:import url="/BASE001.jsp">
<c:param name="head"></c:param>
<c:param name="body">
<c:if test="${not empty testListSubject}">
	<div class="search-results">
		<div class="mb-3">
			<span class="fw-bold">科目：${subject.name}</span>
		</div>
		<form method="post" action="testexecute">
			Add commentMore actions
			<%-- table-borderlessでセルの縦横線をすべて消す --%>
			<table class="table table-borderless mb-0"
				style="vertical-align: middle;">
				<thead>
					<%-- ヘッダー行の下にだけ線を引く --%>
					<tr class="border-bottom">
						<th class="text-center py-2" style="width: 15%;">入学年度</th>
						<th class="text-center py-2" style="width: 15%;">クラス</th>
						<th class="text-center py-2" style="width: 20%;">学生番号</th>
						<th class="text-center py-2" style="width: 30%;">氏名</th>
						<th class="text-center py-2" style="width: 10%;">1回</th>
						<th class="text-center py-2" style="width: 10%;">2回</th>
					</tr>
				</thead>
				<tbody>
					<%-- 取り出したデータを繰り返しで表示 --%>
					<c:forEach var="tLS" items="${testListSubject}" varStatus="loop">
						<%-- 各データ行の下に線を引く (最後の行は除く) --%>
						<tr <c:if test="${!loop.last}">class="border-bottom"</c:if>>
							<%-- 各セルの文字揃えと上下の余白(padding)を調整 --%>
							<td class="text-center py-2">${tLS.entYear}</td>
							<td class="text-center py-2">${tLS.classNum}</td>
							<td class="text-center py-2">${tLS.studentNo}</td>
							<td class="text-center py-2">${tLS.studentName}</td>
							<td class="text-center py-2">
								<%-- デフォルト値をハイフンに設定 --%> <c:set var="point1" value="-" /> <%-- 2. Mapをループ --%>
								<c:forEach var="pointEntry" items="${tLS.points}">
									<%-- キーが1かどうかチェック --%>
									<c:if test="${pointEntry.key == 1}">
										<%-- 一致したら値を上書き --%>
										<c:set var="point1" value="${pointEntry.value}" />
									</c:if>
								</c:forEach> <%-- 最終的な値を表示 --%> ${point1}
							</td>
							<%-- 2回目の点数表示 --%>
							<td class="text-center py-2"><c:set var="point2" value="-" />
								<c:forEach var="pointEntry" items="${tLS.points}">
									<c:if test="${pointEntry.key == 2}">
										<c:set var="point2" value="${pointEntry.value}" />
									</c:if>
								</c:forEach> ${point2}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
</c:if>
<%-- 情報が存在しない場合メッセージを表示 --%>
<c:if test="${empty testListSubject}">
	<p>学生情報が存在しませんでした</p>
</c:if>
</c:param>
</c:import>
>>>>>>> branch 'master' of https://github.com/SIGESPLIDE/JavaSD_teamE.git
