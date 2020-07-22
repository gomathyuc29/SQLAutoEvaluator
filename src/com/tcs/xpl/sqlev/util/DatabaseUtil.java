package com.tcs.xpl.sqlev.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.tcs.xpl.sqlev.constants.ApplicationConstants;

public class DatabaseUtil {

	static Connection con = null;

	public static Connection createConnection() {

		try {
			Class.forName("org.sqlite.JDBC");
			String url = ApplicationConstants.SQLLITE_URL;
			con = DriverManager.getConnection(url);
			System.out.println("Connection to SQLite has been established.");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;

	}

	public static void closecon(Connection con) {
		try {
			con.close();
		} catch (Exception e) {

		}
	}

}
