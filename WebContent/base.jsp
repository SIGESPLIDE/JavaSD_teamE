<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${ param.title }</title>
</head>
<body>
<c:import url="/menu.jsp"/>
${ param.body }
</body>
</html>