<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
	<div class="wrap">
	 <!-- 背景用の動画ファイル -->
	 <video id="bg-video" src="新しいビデオ - コピー_Trim.mp4"  autoplay loop></video>
	</div>
	<div class="hedder">
		<h1>Hello World</h1>

	</div>
	
	<div class="content">
	
		<div class="post">
			
			<c:if test="${message !=null}"> <!-- messageの中がnullじゃなかったら -->
				<p class="message">${message}</p> <!-- message表示(messageの中がnullだったらfalseになるから一切表示されない) -->
			</c:if>
		
			<form class="cp_iptxt" action="DBServlet" method="post">
				<textarea name="text" rows="4" cols="30" class = "textbox-radius" placeholder="What are you doing now？"></textarea>
				<input type="submit" name="button" value="POST" class="button-radius">
				<input type="submit" name="button" value="update" class="button-reload button-radius">
			</form>
		</div>
		<br>
		
		<c:if test="${list !=null}">
			<c:forEach var="i" items="${list}">
				<c:set var="css" value="nofav_icon" />
				<c:if test="${i.favorite}">
					<c:set var="css" value="fav_icon" />
				</c:if>
			
				<div class="tweet">
					<div class="icon"><div class="circle" style="background: url('${image}.png');"></div></div>
					<div>
						<div class="name">${i.username}</div>
						<div class="mention">@${i.username}_${i.mention}-${i.date}</div>
						<div class="content">${i.content}</div>
						<form action="DBServlet" method="post" class="control">
							<input type="submit" name="button" value="" class="button-delete"><input type="hidden" name="id" value="${i.id}">
							<input type="hidden" name="fav_param" value="${i.favorite}">
							<input name="button" class="fav_button ${css}" type="submit" value="favorite">
					</form>
					</div>
				</div>
			</c:forEach>
		</c:if>
	</div>
</body>
</html>