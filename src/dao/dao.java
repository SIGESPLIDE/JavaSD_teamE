package dao;

import java.sql.Connection;

import javax.naming.InitialContext;

import org.apache.tomcat.jdbc.pool.DataSource;

public class dao {
	static DataSource ds;
	 public Connection getConnection() throws Exception {
		 if (ds == null) {
			 InitialContext ic = new InitialContext();

			 ds=(DataSource)ic.lookup("java:/comp/env/jdbc/TEAM_E");
		 }

		 return ds.getConnection();
	 }
}