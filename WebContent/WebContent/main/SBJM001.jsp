<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 作成者のはら -->

<c:import url="/BASE001.jsp">
    <c:param name="head">
        <title>科目管理</title>
    </c:param>
    <c:param name="body">
        <h2>科目管理</h2>

        <div>
            <a href="${pageContext.request.contextPath}/main/SBJM002.jsp">新規登録</a>
        </div>

        <c:choose>
            <c:when test="${empty subjects}">
                <div>科目情報が登録されていません。</div>
            </c:when>
            <c:otherwise>
                <table border="1">
                    <thead>
                        <tr>
                            <th>科目コード</th>
                            <th>科目名</th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="subject" items="${subjects}">
                            <tr>
                                <td>${subject.cd}</td>
                                <td>${subject.name}</td>
                                <td>
                                    <a href="subjectUpdate?cd=${subject.cd}">変更</a>
                                </td>
                                <td>
                                    <a href="subjectDelete?code=${subject.cd}"
                                       onclick="return confirm('本当に削除しますか？');">削除</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </c:param>
</c:import>
