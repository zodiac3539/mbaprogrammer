package usertest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestDAO {
	
	public String testDB() {
		String ret = "Not Good";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			DBCon ctx = new DBCon();
			con = ctx.getConnection();
			
			pstmt = con.prepareStatement("SELECT count(*) FROM TEST");
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				ret = "" + rs.getInt(1);
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {			
			try { rs.close(); } catch(Exception _e) {}
			try { pstmt.close(); } catch(Exception _e) {}
			try { con.close(); } catch(Exception _e) {}
		}
		
		return ret;
	}

}
