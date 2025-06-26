<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>得点管理システム</title>
<link rel="stylesheet" href="main/css/styles.css">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
</head>

<body>
<div class="yohaku">
		${ param.head }
<c:import url="/HEAD001.jsp" />


		<%-- 同一フォルダ内のMENU001.jspをインポートして画面に表示する --%>
<div class="main-container">
<c:import url="/MENU001.jsp" />

			<div class="content-area">${ param.body }</div>
</div>
<%-- 変数：param.bodyに格納されている値を画面の要素として使う --%>
<c:import url="/FOOT001.jsp" />
</div>
