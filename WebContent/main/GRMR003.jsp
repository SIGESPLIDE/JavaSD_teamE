<%@ page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/BASE001.jsp">
    <c:param name="title">成績管理</c:param>
    <c:param name="body">
        <div class="container mt-4">
            <h3 class="mb-4">成績一覧（学生）</h3>

            <div class="card">
                <div class="card-body">
                    <!-- 科目情報 -->
                    <h5 class="card-title mb-3">科目情報</h5>
                    <form action="#" method="get">
                        <div class="row g-3 align-items-end">
                            <!-- 入学年度 -->
                            <div class="col-md-3">
                                <label for="enrollmentYear" class="form-label">入学年度</label>
                                <select class="form-select" id="enrollmentYear" name="enrollmentYear">
                                    <option value="">--------</option>
                                    <%-- ここには入学年度のリストが入ります --%>
                                </select>
                            </div>

                            <!-- クラス -->
                            <div class="col-md-3">
                                <label for="class" class="form-label">クラス</label>
                                <select class="form-select" id="class" name="class">
                                    <option value="">--------</option>
                                    <%-- ここにはクラスのリストが入ります --%>
                                </select>
                            </div>

                            <!-- 科目 -->
                            <div class="col-md-3">
                                <label for="subject" class="form-label">科目</label>
                                <select class="form-select" id="subject" name="subject">
                                    <option value="">--------</option>
                                    <%-- ここには科目のリストが入ります --%>
                                </select>
                            </div>

                            <!-- 検索ボタン -->
                            <div class="col-md-auto">
                                <button type="submit" class="btn btn-primary">検索</button>
                            </div>
                        </div>
                    </form>

                    <!-- 学生情報 -->
                    <hr class="my-4">
                    <h5 class="card-title">学生情報</h5>
                    <form action="#" method="get">
                        <div class="row g-3 align-items-end">
                            <div class="col-md-4">
                                <label for="studentId" class="form-label">学生番号</label>
                                <input type="text" class="form-control" id="studentId" name="studentId"
                                       placeholder="学生番号を入力してください">
                            </div>
                            <div class="col-md-auto">
                                <button type="submit" class="btn btn-secondary">検索</button>
                            </div>
                        </div>
                    </form>

                    <%-- ▼▼▼ ここからが追記部分です ▼▼▼ --%>

                    <!-- 検索結果表示エリア -->
                    <%-- resultListにデータがある場合のみ、このエリアを表示します --%>
                    <c:if test="${not empty resultList}">
                        <hr class="my-4">
                        <h5 class="card-title">検索結果</h5>

                        <%-- divでテーブルを囲み、borderとborder-dangerクラスで赤枠をつけます --%>
                        <div class="table-responsive border border-danger rounded p-2">
                            <table class="table table-hover mb-0">
                                <thead class="table-light">
                                    <tr>
                                        <th>学生番号</th>
                                        <th>氏名</th>
                                        <th>科目</th>
                                        <th>点数</th>
                                        <th>評価</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%-- resultListから1件ずつデータを取り出して表示します --%>
                                    <c:forEach var="result" items="${resultList}">
                                        <tr>
                                            <td>${result.studentNo}</td>
                                            <td>${result.studentName}</td>
                                            <td>${result.subjectName}</td>
                                            <td>${result.point}</td>
                                            <td>${result.evaluation}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>

                    <%-- ▲▲▲ ここまでが追記部分です ▲▲▲ --%>

                </div>
            </div>
        </div>
    </c:param>
</c:import>