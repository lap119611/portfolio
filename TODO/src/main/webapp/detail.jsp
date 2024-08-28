<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="todo.web.Todo, java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TODOタスク管理</title>
</head>
<body>
	<h1>タスク詳細</h1>
	<%
	Todo todo = (Todo) request.getAttribute("todo");
	pageContext.setAttribute("todo", todo);
	%>
	<form action="RegisterServlet" method="POST" enctype="multipart/form-data">
		<table>
			<tr>
				<th>番号</th>
				<%
				if (todo.getId() > 0) {
				%>
				<td>${todo.id}</td>
				<%
				} else {
				%>
				<td>新規</td>
				<%
				}
				%>
			</tr>
			<tr>
				<th>タイトル</th>
				
				<td><input type="text" name="title" value="${todo.title}"
					size="20" /></td>
			</tr>
			<tr>
				<th>タスク内容</th>
				<td><input type="text" name="task" value="${ todo.task }" maxlength="128" size="60"/></td>
			</tr>
			<tr>
				<th>期限</th>
				<td><input type="date" name="limitdate"
					value="${todo.limitdate.toString().substring(0,10)}" size="8" /></td>
			</tr>
			<tr>
				<th>ユーザーID</th>
				<%
				if(todo.getId()>0){
					%>
					<td><input type="text" name="userid" value="${ todo.userid }" size="8"/></td>
					<%
				}else{
					%>
					<td><input type="text" name="userid" value="${loginUser}" size="8"/></td>
					<%
				}
				%>
			</tr>
			<tr>
				<th>状況</th>
				<td>
				<select name="status">
					<option value="0" <%if (todo.getStatus() == 0) {%> selected <%}%>>未着手</option>
					<option value="1" <%if (todo.getStatus() == 1) {%> selected <%}%>>着手</option>
					<option value="2" <%if (todo.getStatus() == 2) {%> selected <%}%>>完了</option>
					<option value="3" <%if (todo.getStatus() == 3) {%> selected <%}%>>保留</option>
				</select>
				</td>
			</tr>
			<tr>
				<th>添付ファイル</th>
				<td><input type="file" name="uploadfile" />${todo.filename}</td>
			</tr>
		</table>
		<br>
		<input type="hidden" name="id" value="${todo.id}" /> 
		<input type="submit" value="登録" />
	</form>
	<%
		if(todo.getId() > 0){
	%>
	<br>
	<form action="DeleteServlet" method="POST">
		<input type="hidden" name="id" value="${ todo.id }" />
		<input type="submit" value="削除" />
	</form>
	<%
		}
	%>
	
	<ul>
	<%
		List<String> msgs = (List<String>)request.getAttribute("errorMessages");
	if(msgs != null){
		for(int i = 0; i < msgs.size(); i++){
			String msg = msgs.get(i);
	%>
		<li><%= msg %></li>
	<%
			
		}
	}
	%>
	</ul>
</body>
</html>