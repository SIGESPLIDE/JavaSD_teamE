<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- BASE001.jspをテンプレートとして使用 --%>
<c:import url="/BASE001.jsp">
    <%-- ページのタイトルを設定 --%>
    <c:param name="head">成績管理</c:param>

    <%-- ページの本文部分を設定 --%>
    <c:param name="body">
        <div class="container mt-4">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <h5 class="mb-0">成績管理</h5>
                        </div>
                        <div class="card-body">
                            <%-- コントローラーからのエラーメッセージ、またはリダイレクト時のエラーメッセージを表示 --%>
                            <c:if test="${not empty errorMessage or not empty param.errorMessage}">
                                <div class="alert alert-danger" role="alert">
                                    ${errorMessage} ${param.errorMessage}
                                </div>
                            </c:if>

                            <%-- 検索フォーム: GETメソッドでコントローラーに送信 --%>
                            <form action="ExamRegist" method="get">
                                <div class="row align-items-end">
                                    <%-- 入学年度: コントローラーからの entYearList を使用 --%>
                                    <div class="col-md-auto mb-3">
                                        <label for="entYear" class="form-label">入学年度</label>
                                        <select class="form-select" id="entYear" name="f1">
                                            <option value="">--------</option>
                                            <c:forEach var="year" items="${entYearList}">
                                                <%-- 検索後の値(selectedEntYear)を保持 --%>
                                                <option value="${year}" <c:if test="${year == selectedEntYear}">selected</c:if>>${year}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <%-- クラス: コントローラーからの classList を使用 --%>
                                    <div class="col-md-auto mb-3">
                                        <label for="classNum" class="form-label">クラス</label>
                                        <select class="form-select" id="classNum" name="f2">
                                            <option value="">--------</option>
                                            <c:forEach var="classNum" items="${classList}">
                                                <option value="${classNum}" <c:if test="${classNum == selectedClassNum}">selected</c:if>>${classNum}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <%-- 科目: コントローラーからの subjectList を使用 --%>
                                    <div class="col-md-3 mb-3">
                                        <label for="subject" class="form-label">科目</label>
                                        <select class="form-select" id="subject" name="f3">
                                            <option value="">--------</option>
                                            <c:forEach var="subject" items="${subjectList}">
                                                <option value="${subject.cd}" <c:if test="${subject.cd == selectedSubjectCd}">selected</c:if>>${subject.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <%-- 回数 --%>
                                    <div class="col-md-auto mb-3">
                                        <label for="testNo" class="form-label">回数</label>
                                        <select class="form-select" id="testNo" name="f4">
                                            <option value="">--------</option>
                                            <c:forEach var="i" begin="1" end="10">
                                                <option value="${i}" <c:if test="${i == selectedTestNo}">selected</c:if>>${i}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-auto mb-3">
                                        <button type="submit" class="btn btn-secondary">検索</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <%-- 検索結果表示 & 登録フォーム: コントローラーからの examResults を使用 --%>
                    <c:if test="${not empty examResults}">
                        <hr>
                        <%-- 検索された科目名と回数を表示 (SubjectオブジェクトはexamResultsの最初の要素から取得) --%>
                        <h5 class="mt-4">科目: ${examResults[0].subject.name} (${selectedTestNo}回)</h5>

                        <%-- 登録フォーム: POSTメソッドでコントローラーに送信 --%>
                        <form action="ExamRegist" method="post">
                            <%-- 登録処理に必要な検索条件を隠しフィールドで渡す --%>
                            <input type="hidden" name="f1" value="${selectedEntYear}">
                            <input type="hidden" name="f2" value="${selectedClassNum}">
                            <input type="hidden" name="f3" value="${selectedSubjectCd}">
                            <input type="hidden" name="f4" value="${selectedTestNo}">

                            <table class="table table-hover mt-3">
                                <thead>
                                    <tr>
                                        <th>入学年度</th>
                                        <th>クラス</th>
                                        <th>学生番号</th>
                                        <th>氏名</th>
                                        <th style="width: 15%;">点数</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="exam" items="${examResults}">
                                        <%-- コントローラーが必要とする学生番号とクラス番号を隠しフィールドで送信 --%>
                                        <input type="hidden" name="student_no" value="${exam.student.no}">
                                        <input type="hidden" name="class_num_${exam.student.no}" value="${exam.classNum}">

                                        <tr>
                                            <td>${exam.student.entYear}</td>
                                            <td>${exam.classNum}</td>
                                            <td>${exam.student.no}</td>
                                            <td>${exam.student.name}</td>
                                            <td>
                                                <input type="number" class="form-control" name="point_${exam.student.no}" value="${exam.point}" min="0" max="100">
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <div class="text-center mt-3">
                                <button type="submit" class="btn btn-primary">登録して終了</button>
                            </div>
                        </form>
                    </c:if>
                </div>
            </div>
        </div>
    </c:param>
</c:import>