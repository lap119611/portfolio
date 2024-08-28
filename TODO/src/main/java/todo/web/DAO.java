package todo.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DAO implements AutoCloseable {
	private Connection connection = null;

	public DAO() {
	}

	// 接続
	public Connection getConnection() throws Exception {
		try {
			// DBとの接続がない場合に限り、接続する
			if (connection == null || connection.isClosed()) {
				// アプリケーションコンテキストからリソースURIを取得する
				InitialContext initCtx = new InitialContext();
				DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/localDB");
				connection = ds.getConnection();
			}
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
			connection = null;
			throw e;
		}
		return connection;
	}

	public PreparedStatement getPreparedStatement(String sql) throws Exception {
		return getConnection().prepareStatement(sql);
	}

	public void commit() throws SQLException {
		connection.commit();
	}

	public void rollback() throws SQLException {
		connection.rollback();
	}

	@Override
	public void close() throws Exception {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection = null;
		}
	}

}
