<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/BASE001.jsp">
 <c:param name="head">
        <title>科目管理</title>
    </c:param>
    <c:param name="body">
<c:if test="${not empty errorMessage}">
    <div style="color: red; margin-bottom: 10px;">
        ${errorMessage}
    </div>
</c:if>

<!-- 科目名入力エラーの表示 -->
<c:if test="${not empty subjectNameError}">
    <span style="color: orange; font-size: 0.9em;">
        ${subjectNameError}
    </span>
</c:if>
</c:param>
</c:import>