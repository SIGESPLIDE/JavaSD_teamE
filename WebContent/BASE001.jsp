<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>得点管理システム</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/styles.css">
    ${ param.head }
</head>

<body>
<c:import url="/HEAD001.jsp"/>
${ param.body }
    

	
<%-- 同一フォルダ内のMENU001.jspをインポートして画面に表示する --%>
<c:import url="/MENU001.jsp"/>
<%-- 変数：param.bodyに格納されている値を画面の要素として使う --%>
<c:import url="/FOOT001.jsp"/>

</body>
