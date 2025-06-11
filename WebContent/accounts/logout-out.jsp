<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:import url="/base.jsp">

    <c:param name="title">ログアウト完了</c:param>

    <c:param name="body">
        <p>ログアウトしました</p>

        <%--
          アプリケーションのトップページ（main）へ戻るリンクを表示します。
          「contextPath」はアプリケーションのルートパスを自動的に補完します。
        --%>
        <a href="${pageContext.request.contextPath}/main">topページに戻る</a>
    </c:param>
</c:import>
