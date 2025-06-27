<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 作成者のはら -->
<c:import url="/BASE001.jsp">
    <c:param name="head">
        <title>学生管理</title>
    </c:param>
    <c:param name="body">
        <h2>学生情報変更完了</h2>

        <p>学生の情報を更新しました。</p>

        <a href="/JavaSD/main/subject">学生一覧へ</a>
    </c:param>
</c:import>
