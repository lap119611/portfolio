package todo.web;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO extends DAO {
	public List<Todo> todoList() throws Exception {
		List<Todo> returnList = new ArrayList<Todo>();
		
		String sql = "SELECT id, title, task, limitdate, lastupdate, userid, "
				+ " label, td.status, filename FROM todo_list AS td"
				+ " left outer join status_list as st"
				+ " ON st.status = td.status"
				+ " order by id";
		
		PreparedStatement statement = getPreparedStatement(sql);
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()) {
			Todo dto = new Todo();
			dto.setId(rs.getInt("id"));
			dto.setTitle(rs.getString("title"));
			dto.setTask(rs.getString("task"));
			dto.setLimitdate(rs.getTimestamp("limitdate"));
			dto.setLastupdate(rs.getTimestamp("lastupdate"));
			dto.setUserid(rs.getString("userid"));
			dto.setLabel(rs.getString("label"));
			dto.setFilename(rs.getString("filename"));
			returnList.add(dto);
		}
		return returnList;
		
	}
	
	public Todo detail(int id) throws Exception{
		String sql = "SELECT id, title, task, limitdate, lastupdate, userid, "
				+ " label, td.status, filename FROM todo_list AS td"
				+ " left outer join status_list as st"
				+ " ON st.status = td.status"
				+ " where id = ?";

		PreparedStatement statement = getPreparedStatement(sql);
		statement.setInt(1,  id);
		ResultSet rs = statement.executeQuery();
		
		Todo dto = new Todo();
		while(rs.next()) {
			dto.setId(rs.getInt("id"));
			dto.setTitle(rs.getString("title"));
			dto.setTask(rs.getString("task"));
			dto.setLimitdate(rs.getTimestamp("limitdate"));
			dto.setLastupdate(rs.getTimestamp("lastupdate"));
			dto.setUserid(rs.getString("userid"));
			dto.setLabel(rs.getString("label"));
			dto.setStatus(rs.getInt("status"));
			dto.setFilename(rs.getString("filename"));
		}
		return dto;
	}
	
	public int delete(int id) throws Exception {
		String sql = "delete from todo_list where id = ?";
		
		int result = 0;
		try {
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setInt(1, id);
			result = statement.executeUpdate();
			
			super.commit();
		}catch(Exception e) {
			super.rollback();
			throw e;
		}
		return result;
	}
	
	public int deleteAll() throws Exception {
		String sql = "delete from todo_list";
		
		int result = 0;
		try {
			PreparedStatement statement = getPreparedStatement(sql);
			result = statement.executeUpdate();
			
			super.commit();
		}catch(Exception e) {
			super.rollback();
			throw e;
		}
		return result;
	}
	
	public int registerInsert(Todo dto)throws Exception {
		String sql = "insert into todo_list(title, task, limitdate, lastupdate,"
				+ " userid, status, filename) values (?,?,?,now(),?,?,?)";
		
		int result = 0;
		try {
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setString(1, dto.getTitle());
			statement.setString(2, dto.getTask());
			statement.setString(3, dto.getInputLimitdate());
			statement.setString(4, dto.getUserid());
			statement.setInt(5, dto.getStatus());
			statement.setString(6, dto.getFilename());
			result = statement.executeUpdate();
			super.commit();

		}catch(Exception e) {
			super.rollback();
			throw e;
		}
		return result;
	}
	
	public int registerUpdate(Todo dto) throws Exception {
		String sql = "update todo_list set title = ?,task = ?, limitdate = ?, lastupdate = now(), userid = ?, status = ?, filename = ? where id = ?";
		
		int result = 0;
		try {
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setString(1, dto.getTitle());
			statement.setString(2, dto.getTask());
			statement.setString(3, dto.getInputLimitdate());
			statement.setString(4, dto.getUserid());
			statement.setInt(5, dto.getStatus());
			statement.setString(6, dto.getFilename());
			statement.setInt(7,  dto.getId());
			result = statement.executeUpdate();
			super.commit();

		}catch(Exception e) {
			super.rollback();
			throw e;
		}
		return result;
	}
}
