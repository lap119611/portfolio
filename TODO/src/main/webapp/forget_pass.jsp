<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>パスワード忘れ</title>
</head>
<body>
<p>パスワードをメール通知します。</p>
<form action="MailPasswordServlet" method="POST">
<p>ユーザーID:<input type="text" name="userid"></p>
<p>メールアドレス:<input type="email" name="mailaddress"></p>
<input type="submit" value="送信">
</form>
</body>
</html>