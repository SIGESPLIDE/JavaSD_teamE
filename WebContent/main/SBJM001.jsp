<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/BASE001.jsp">
    <c:param name="head">
        <title>科目管理</title>
    </c:param>
    <c:param name="body">
        <h2>科目管理</h2>
        <div>
            <a href="SMJM2.jsp">新規登録</a>
        </div>

        <%
            // 仮の科目データ。実際にはデータベースから取得します。
            java.util.List<java.util.Map<String, String>> subjects = new java.util.ArrayList<>();


            if (subjects.isEmpty()) {
        %>
            <div>科目情報が登録されていません。</div>
        <%
            } else {
        %>
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
                    <%
                        for (java.util.Map<String, String> subject : subjects) {
                    %>
                        <tr>
                            <td><%= subject.get("code") %></td>
                            <td><%= subject.get("name") %></td>
                            <td><a href="SMJM2.jsp?action=edit&code=<%= subject.get("code") %>">変更</a></td>
                            <td><a href="#" onclick="return confirm('本当に削除しますか？');">削除</a></td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        <%
            }
        %>
    </c:param>
</c:import>