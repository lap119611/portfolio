<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="todo.web.Todo,java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TODOタスク管理</title>
</head>
<body>
	<h1>タスク一覧</h1>
	<%
	String loginUser = (String)request.getUserPrincipal().getName();
	session.setAttribute("loginUser", loginUser);
	%>
	<p>ログインユーザー：${ loginUser }</p>
	<table border="1">
		<tr>
			<th>番号</th>
			<th>タイトル</th>
			<th>タスク内容</th>
			<th>期限</th>
			<th>最終更新</th>
			<th>ユーザーID</th>
			<th>状況</th>
			<th>詳細画面へ</th>
			<th>添付ファイル</th>
		</tr>
		<%
		List<Todo> todos = (List<Todo>)request.getAttribute("todoList");
		if(todos != null){
			Todo todo;
			for(int i = 0; i < todos.size(); i++){
				todo = todos.get(i);
		%>
		<tr>
			<td><%=todo.getId()%></td>
			<td><%=todo.getTitle()%></td>
			<td><%=todo.getTask()%></td>
			<td><%=todo.getLimitdate().toString().substring(0, 10)%></td>
			<td><%=todo.getLastupdate()%></td>
			<td><%=todo.getUserid()%></td>
			<td><%=todo.getLabel()%></td>
			<td><a href="DetailServlet?id=<%=todo.getId() %>">詳細画面へ</a></td>
<%
			if(todo.getFilename() != null){
				%>
				<td><a href="<%=todo.getFilename()%>" download><%=todo.getFilename() %></a></td>
				
				<%
				
			} else {
				%>
				<td>なし</td>
				<%
			}
%>
		</tr>
		<%
				
			}
		}
		%>
	</table>
	<br>
	<form action="DetailServlet" method="GET">
		<input type="hidden" name="id" value="0" />
		<input type="submit" value="新規" />
	</form>
	<br>
	<form action="DeleteAllServlet" method="GET">
		<input type="submit" value="一括削除" />
	</form>
</body>
</html>