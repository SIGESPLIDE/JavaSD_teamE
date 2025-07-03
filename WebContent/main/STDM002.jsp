<%--
  ファイル名：STDM002.jsp
  機能概要：学生登録画面
--%>

<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/BASE001.jsp">
    <c:param name="head">
        <%-- このJSP専用のスタイルシートがあればここに記述 --%>
        <%-- <link rel="stylesheet" href="styles.css"> --%>
    </c:param>
    <c:param name="body">
        <div class="container" style="max-width: 600px;">
            <h2 class="my-4">学生情報登録</h2>

            <%-- システム全体のエラーがあれば表示 --%>
            <c:if test="${not empty errors.system}">
                <div class="alert alert-danger">${errors.system}</div>
            </c:if>

            <form action="StudentCreateExcute" method="post">
                <%-- ★★★ 入学年度 ★★★ --%>
                <div class="mb-3">
                    <label for="ent_year" class="form-label">入学年度</label>
                    <%-- ★ 修正: is-invalidクラスを削除 --%>
                    <select class="form-select" id="ent_year" name="ent_year" required>
                        <option value="0">---------</option>
                        <c:forEach var="year" items="${ent_year_list}">
                            <option value="${year}" <c:if test="${year eq ent_year}">selected</c:if>>${year}</option>
                        </c:forEach>
                    </select>
                    <%-- ★ 修正: エラーメッセージのスタイルを直接指定 --%>
                    <c:if test="${not empty errors.ent_year}">
                        <div style="color: #fd7e14; font-size: 0.875em;">${errors.ent_year}</div>
                    </c:if>
                </div>

                <%-- ★★★ 学生番号 ★★★ --%>
                <div class="mb-3">
                    <label for="no" class="form-label">学生番号</label>
                    <%-- ★ 修正: is-invalidクラスを削除 --%>
                    <input type="text" class="form-control"
                           id="no" name="no" value="${no}" required>
                    <%-- ★ 修正: エラーメッセージのスタイルを直接指定 --%>
                    <c:if test="${not empty errors.no_empty}">
                        <div style="color: #fd7e14; font-size: 0.875em;">${errors.no_empty}</div>
                    </c:if>
                    <c:if test="${not empty errors.no_duplicate}">
                        <div style="color: #fd7e14; font-size: 0.875em;">${errors.no_duplicate}</div>
                    </c:if>
                </div>

                <%-- ★★★ 氏名 ★★★ --%>
                <div class="mb-3">
                    <label for="name" class="form-label">氏名</label>
                    <%-- ★ 修正: is-invalidクラスを削除 --%>
                    <input type="text" class="form-control"
                           id="name" name="name" value="${name}" required>
                    <%-- ★ 修正: エラーメッセージのスタイルを直接指定 --%>
                    <c:if test="${not empty errors.name}">
                        <div style="color: #fd7e14; font-size: 0.875em;">${errors.name}</div>
                    </c:if>
                </div>

                <%-- ★★★ クラス ★★★ --%>
                <div class="mb-3">
                    <label for="class_num" class="form-label">クラス</label>
                    <select class="form-select" id="class_num" name="class_num" required>
                        <c:if test="${not empty class_num_list}">
                            <option value="">---------</option>
                            <c:forEach var="c" items="${class_num_list}">
                                <option value="${c}" <c:if test="${c == class_num}">selected</c:if>>${c}</option>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty class_num_list}">
                            <option value="">登録可能なクラスがありません</option>
                        </c:if>
                    </select>
                </div>

                <div class="d-flex align-items-center">
                    <button type="submit" class="btn btn-primary">登録して終了</button>
                    <a href="STDM"001 class="ms-3">戻る</a>
                </div>
            </form>
        </div>
    </c:param>
</c:import>