<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/BASE001.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<!-- 作成者　潟辺　陸 -->
<!-- まだ選択した科目が表示できません -->
<!-- 一部エラーあり -->
<h2>科目情報削除</h2>

   <form action="DeleteSubjectt" method="post">
        <label for="id">を削除してもよろしいですか:</label>
        <input type="text" id="id" name="id">

<button type="submit">削除</button>

<a href="SBJM001.jsp">戻る</a>

</form>

</body>
</html>