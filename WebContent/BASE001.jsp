<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<%-- 変数：param.titleに格納されている値をタイトルとして使う --%>
	<title>${ param.title }</title>
</head>
<body>
<%-- 同一フォルダ内のmenu.jspをインポートして画面に表示する --%>
<c:import url="/MENU001.jsp"/>
<%-- 変数：param.bodyに格納されている値を画面の要素として使う --%>

${ param.body }
</body>
</html>