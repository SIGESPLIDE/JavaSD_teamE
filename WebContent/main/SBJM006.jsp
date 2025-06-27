<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/BASE001.jsp">
<html>
<c:param name="head">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</c:param>
    <c:param name="body">
<!-- 作成者　潟辺　陸 -->
<!-- 選択した科目を表示するコードがまだ完成していません -->
<h2>科目情報削除</h2>

   <div>
   <form action="SubjectDao.jsp" method="post">
        <label for="id">を削除してもよろしいですか:</label>
        <input type="text" id="id" name="id">


<button type="submit">削除</button>

<a href="SBJM001.jsp">戻る</a>

</form>

  </div>

</c:param>
</html>
</c:import>