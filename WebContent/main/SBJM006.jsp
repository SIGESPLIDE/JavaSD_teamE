<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 作成者　潟辺　陸 -->
<c:import url="/BASE001.jsp">

	<c:param name="head">
		<h2>科目情報削除</h2>
	</c:param>

	<c:param name="body">

		<form action="subjectDeleteExcute" method="post">
			<label for="id">削除するID:</label> <input type="hidden" name="id"
				value="${param.id}" />
			<button type="submit">削除</button>
		</form>

		<div>
			<a href="SBJM001.jsp">戻る</a>
		</div>


	</c:param>

</c:import>