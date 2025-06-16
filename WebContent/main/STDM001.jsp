<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/BASE001.jsp">
<c:param name="head">
    <title>学生管理</title>

</c:param>
<c:param name="body">
    <h2>学生管理</h2>

    <!-- 検索条件 -->
    <div class="search-area">
        <form action="StudentListServlet" method="get">
            <label>入学年度</label>
            <select name="entYear">
                <option value="">--</option>
                <c:forEach var="year" items="${entYearList}">
                    <option value="${year}" ${param.entYear == year ? 'selected' : ''}>${year}</option>
                </c:forEach>
            </select>

            <label>クラス</label>
            <select name="classId">
                <option value="">--</option>
                <c:forEach var="cls" items="${classList}">
                    <option value="${cls.id}" ${param.classId == cls.id ? 'selected' : ''}>${cls.name}</option>
                </c:forEach>
            </select>

            <label><input type="checkbox" name="isEnrolled" value="1" ${param.isEnrolled == '1' ? 'checked' : ''}/>在学中</label>

            <button type="submit">絞り込み</button>
        </form>
        <a href="StudentRegisterServlet">新規登録</a>
    </div>

    <!-- 検索結果 -->
    <div class="result-area">
        <c:choose>
            <c:when test="${not empty studentList}">
                <div>検索結果：${fn:length(studentList)}件</div>
                <table>
                    <thead>
                        <tr>
                            <th>入学年度</th>
                            <th>学生番号</th>
                            <th>氏名</th>
                            <th>クラス</th>
                            <th>在学中</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="stu" items="${studentList}">
                            <tr>
                                <td>${stu.entYear}</td>
                                <td>${stu.studentNo}</td>
                                <td>${stu.name}</td>
                                <td>${stu.className}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${stu.isEnrolled}">○</c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                </td>
                                <td><a href="StudentEditServlet?studentNo=${stu.studentNo}">変更</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div>学生情報は存在しませんでした</div>
            </c:otherwise>
        </c:choose>
    </div>
</c:param>
</c:import>
