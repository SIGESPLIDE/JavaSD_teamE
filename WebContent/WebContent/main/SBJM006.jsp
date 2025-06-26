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
<h2>科目情報削除</h2>
<div>

<form action="subjectDelete" method="post">

<div>
    <p>科目ID: <%= id %></p>
    <p>科目名: <%= getName %></p>

    <form action="subjectDelete" method="post" onsubmit="return confirm('を削除してもよろしいですか？');">
        <input type="hidden" name="id" value="<%= id %>">
        <button type="submit">削除</button>
    </form>
</div>

<button type="submit">削除</button>

</form>

</div>

<a href="SBJM001.jsp">戻る</a>

</body>
</html>