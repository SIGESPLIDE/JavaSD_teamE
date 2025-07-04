<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/BASE001.jsp">
	<c:param name="head">
		<h2>科目情報削除</h2>
	</c:param>

	<c:param name="body">

		<p style="background-color: #98C9A3; text-align: center; color: #333; padding: 15px; margin-bottom: 20px;">
			削除が完了しました
		</p>

		<a href="subject">科目一覧</a>
	</c:param>
</c:import>