<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>REDIS Test</title>
</head>
<body>
헬로월드 :<br>
<ul>
<c:if test="${not empty article }">
	제목 : ${article.subject }<br>
	조회수(디비) : ${article.numread}<br>
	조회수(레디스) : ${article.numreadRedis}<br>
	내용 : ${article.content }<br>
	리스트로 <a href="/">가기</a>
</c:if>
<c:forEach items="${articles }" var="article">
	<li> DB로 <a href="/event/jpa/${article.id }">읽기</a>, REDIS로 <a href="/event/redis/${article.id}">읽기</a>=제목 : ${article.subject }</li>
</c:forEach>
</ul>
<hr>
<h3>글쓰기</h3>
<form method="post" action="/event">
	제목 :<input type="text" name="subject"><br>
	내용 : <Br>
	<textarea rows="5" cols="30" name="content"></textarea><br>
	<input type="submit" value="전송">
</form>
</body>
</html>