<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>top</title>
<link rel="stylesheet" href="top.css">
<style type="text/css">
label{
	display: block;
	float: left;
	width: 60px;
}
</style>
</head>
<body>
	<div>
		<h1>Login</h1>
		<p>${message}</p>　<!-- messageはログアアウトしましたになる -->
		<form action="Login" method="post">
			<label>Name: </label>
			<input type="text" name="name">
			<br>
			<label>Pass: </label>
			<input type="password" name="pass">
			<br>

			<label>Image:</label>
			<br>
			<select class="piuture" name="img" >
			    <option value="nomal">Default</option>
			    <option value="spring">Spring</option>
			    <option value="summer">Summer</option>
			    <option value="autumn">Autumn</option>
			    <option value="winter">Winter</option>
			</select>
			<br>
			<input class="submit" type="submit" name="button" value="login">
		</form>
	</div>
</body>
</html>