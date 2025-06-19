<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/BASE001.jsp">
    <c:param name="title">成績管理</c:param>

    <c:param name="body">
        <div class="container mt-4">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <h5 class="mb-0">成績管理</h5>
                        </div>
                        <%-- 科目別検索 --%>
                        <div class="card-body">
                            <%-- form-inline を削除 --%>
                            <form action="#" method="get">
                                <%-- フォーム要素全体を新しい行で囲み、align-items-end で下揃え --%>
                                <div class="row align-items-end">
                                    <%-- 各フォームグループとボタンを col-md-auto で囲む --%>
                                    <div class="col-md-auto mb-3">
                                        <label for="enrollmentYear" class="form-label">入学年度</label>
                                        <select class="form-control" id="enrollmentYear" name="enrollmentYear">
                                            <option value="">--------</option> <%-- Placeholder as per image --%>
                                            <c:forEach var="s" items="${list}">
                                                <option value="${s.studentId}">${s.studentName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-auto mb-3">
                                        <label for="class" class="form-label">クラス</label>
                                        <select class="form-control" id="class" name="class">
                                            <option value="">--------</option> <%-- Placeholder as per image --%>
                                            <c:forEach var="s" items="${list}">
                                                <option value="${s.studentId}">${s.studentName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <label for="subject" class="form-label">科目</label>
                                        <select class="form-control" id="subject" name="subject">
                                            <option value="">--------</option> <%-- Placeholder as per image --%>
                                            <c:forEach var="s" items="${list}">
                                                <option value="${s.studentId}">${s.studentName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="col-md-auto mb-3 ms-4"> <%-- ボタンもカラムで囲む --%>
                                        <button type="submit" class="btn btn-secondary">検索</button>

                                    <%-- 学生別検索 --%>


                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:param>
</c:import>