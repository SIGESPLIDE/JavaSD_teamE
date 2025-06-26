<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/BASE001.jsp">
    <c:param name="title">成績管理</c:param>
    <c:param name="body">
        <div class="container mt-4">
            <h3 class="mb-4">成績参照</h3> <!-- ① -->

            <div class="card">
                <div class="card-body">
                    <!-- 科目情報 ② -->
                    <h5 class="card-title mb-3">科目情報</h5>

                    <form action="#" method="get">
                        <div class="row g-3 align-items-end">
                            <!-- 入学年度 ③ -->
                            <div class="col-md-3">
                                <label for="enrollmentYear" class="form-label">入学年度</label> <!-- ③ -->
                                <select class="form-select" id="enrollmentYear" name="enrollmentYear"> <!-- ⑥ -->
                                    <option value="">--------</option>

                                    <%-- データベースから情報を持ってきている --%>
                                    <c:forEach var="selectEntYear" items="${entYearList}">
                                        <option value="${selectEntYear}"<c:if test="${selectEntYear == entYear}">selected</c:if>>${selectEntYear}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- クラス ④ -->
                            <div class="col-md-3">
                                <label for="class" class="form-label">クラス</label> <!-- ④ -->
                                <select class="form-select" id="class" name="class"> <!-- ⑦ -->
                                    <option value="">--------</option>
                                    <c:forEach var="course" items="${classNumList}">
                                        <option value="${course.class_num}"<c:if test="${course.class_num == classNum}">selected</c:if>>${course.class_num}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- 科目 ⑤ -->
                            <div class="col-md-3">
                                <label for="subject" class="form-label">科目</label> <!-- ⑤ -->
                                <select class="form-select" id="subject" name="subject"> <!-- ⑧ -->
                                    <option value="">--------</option>
                                    <c:forEach var="subject" items="${subjectList}">
                                        <option value="${subject.cd}"<c:if test="${subject.cd == subjectCd}">selected</c:if>>${subject.cd}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- 検索ボタン ⑨ -->
                            <div class="col-md-auto">
                                <button type="submit" class="btn btn-primary">検索</button>
                            </div>
                        </div>
                    </form>

                    <!-- 学生情報 ⑩ -->
                    <hr class="my-4">
                    <h5 class="card-title">学生情報</h5>

                    <form action="#" method="get">
                        <div class="row g-3 align-items-end">
                            <div class="col-md-4">
                                <label for="studentId" class="form-label">学生番号</label> <!-- ⑪ -->
                                <input type="text" class="form-control" id="studentId" name="studentId"
                                       placeholder="学生番号を入力してください"> <!-- ⑫ -->
                            </div>
                            <div class="col-md-auto">
                                <button type="submit" class="btn btn-secondary">検索</button> <!-- ⑬ -->
                            </div>
                        </div>
                    </form>

                    <!-- 補足メッセージ ⑭ -->
                    <div class="mt-3 text-muted">毎項目を選択または学生情報を入力して検索ボタンをクリックしてください</div> <!-- ⑭ -->

                </div>

            </div>
        </div>
    </c:param>
</c:import>
