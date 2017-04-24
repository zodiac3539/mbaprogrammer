package usertest;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBCon {
	private static DataSource ds = null;

	public DBCon() {
		
    }
	
	public Connection getConnection2() {
		Connection con = null;
		
		
		return con;
	}
	
	public Connection getConnection() {		
		String context = "java:comp/env/jdbc/TestDB";
		try {
			if(ds == null) {
				Context cxt = new InitialContext();
				ds = (DataSource) cxt.lookup(context);
				
			}
			if(ds == null) {
				System.err.println("No Data Source!");
			}
			return ds.getConnection();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
