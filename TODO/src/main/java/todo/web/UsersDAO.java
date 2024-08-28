package todo.web;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsersDAO extends DAO {
	public Users getPassword(String userid) throws Exception {
		String sql = "SELECT userid, password FROM users WHERE userid = ?";

		PreparedStatement statement = getPreparedStatement(sql);
		statement.setString(1, userid);
		ResultSet rs = statement.executeQuery();
		Users dto = new Users();
		while (rs.next()) {
			dto.setUserid(rs.getString("userid"));
			dto.setPassword(rs.getString("password"));
		}
		
		return dto;
	}

}
