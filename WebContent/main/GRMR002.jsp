<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/BASE001.jsp">
    <c:param name="title">成績管理</c:param>
    <c:param name="body">
        <div class="container mt-4">
            <!-- タイトル -->
            <h3 class="mb-4">成績一覧（科目）</h3>

            <!-- 検索フォーム -->
            <div class="card">
                <div class="card-body">
                    <!-- 科目情報 -->
                    <h5 class="card-title mb-3">検索条件</h5>

                    <!-- 単一のフォームに統一 -->
                    <form action="TestList.action" method="get">
                        <div class="row g-3 align-items-end">
                            <!-- 入学年度 -->
                            <div class="col-md-3">
                                <label for="enrollmentYear" class="form-label">入学年度</label>
                                <select class="form-select" id="enrollmentYear" name="enrollmentYear">
                                    <option value="">--------</option>
                                    <c:forEach var="year" items="${enrollmentYears}">
                                        <option value="${year}" ${param.enrollmentYear == year ? 'selected' : ''}>${year}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- クラス -->
                            <div class="col-md-3">
                                <label for="class" class="form-label">クラス</label>
                                <select class="form-select" id="class" name="class">
                                    <option value="">--------</option>
                                    <c:forEach var="cls" items="${classList}">
                                        <option value="${cls}" ${param.class == cls ? 'selected' : ''}>${cls}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- 科目 -->
                            <div class="col-md-3">
                                <label for="subject" class="form-label">科目</label>
                                <select class="form-select" id="subject" name="subject">
                                    <option value="">--------</option>
                                    <c:forEach var="sub" items="${subjectList}">
                                        <option value="${sub}" ${param.subject == sub ? 'selected' : ''}>${sub}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- 学生番号 -->
                            <div class="col-md-3">
                                <label for="studentId" class="form-label">学生番号</label>
                                <input type="text" class="form-control" id="studentId" name="studentId"
                                       value="${param.studentId}" placeholder="学生番号を入力してください">
                            </div>

                            <!-- 検索ボタン -->
                            <div class="col-md-auto">
                                <button type="submit" class="btn btn-primary">検索</button>
                            </div>
                        </div>
                    </form>

                    <!-- 補足メッセージ -->
                    <div class="mt-3 text-muted">条件を選択または学生番号を入力して検索してください</div>
                </div>
            </div>

            <!-- 成績結果テーブル -->
            <c:if test="${not empty scoreList}">
                <div class="mt-5">
                    <!-- 科目名表示 -->
                    <h5>科目：<span class="text-primary">${subjectName}</span></h5>

                    <div class="table-responsive mt-3">
                        <table class="table table-bordered table-hover align-middle text-center">
                            <thead class="table-light">
                                <tr>
                                    <th>入学年度</th>
                                    <th>クラス</th>
                                    <th>学生番号</th>
                                    <th>氏名</th>
                                    <th>1回目</th>
                                    <th>2回目</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="record" items="${scoreList}">
                                    <tr>
                                        <td>${record.enrollmentYear}</td>
                                        <td>${record.className}</td>
                                        <td>${record.studentId}</td>
                                        <td>${record.studentName}</td>
                                        <td>${record.firstScore}</td>
                                        <td>${record.secondScore}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:if>
        </div>
    </c:param>
</c:import>
