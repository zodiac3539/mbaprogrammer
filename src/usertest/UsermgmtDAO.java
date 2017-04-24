package usertest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsermgmtDAO {

	
	public UsermgmtDAO() {
		
	}
	
	public UsermgmtVO getUserwithID(String userid) {
		UsermgmtVO usermgmtVO = new UsermgmtVO();
		
		Connection con = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		try {
			DBCon dbcon = new DBCon();
			con = dbcon.getConnection();
			
			String sql = "SELECT userid, userpw, username, userlevel FROM usermgmt WHERE userid=?";
			ptmt = con.prepareStatement(sql);
			ptmt.setString(1, userid);
			rs = ptmt.executeQuery();
			
			if(rs.next()) {
				usermgmtVO.setUserid(rs.getString("userid"));
				usermgmtVO.setUserpw(rs.getString("userpw"));
				usermgmtVO.setUsername(rs.getString("username"));
				usermgmtVO.setUserlevel(rs.getInt("userlevel"));
				
			} else {
				usermgmtVO.setUserid("No ID");
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try { rs.close(); } catch (Exception _e) {}
			try { ptmt.close(); } catch (Exception _e) {}
			try { con.close(); } catch (Exception _e) {}			
		}
		
		return usermgmtVO;
	}
	
	public void createNewUser(UsermgmtVO usermgmtVO) {
		Connection con = null;
		PreparedStatement ptmt = null;
		//ResultSet rs = null;
		
		try {
			DBCon dbcon = new DBCon();
			con = dbcon.getConnection();
			
			String sql = "INSERT INTO usermgmt(userid, userpw, username, userlevel) VALUES(?, ?, ?, 3)";
			ptmt = con.prepareStatement(sql);
			ptmt.setString(1, usermgmtVO.getUserid());
			ptmt.setString(2, usermgmtVO.getUserpw());
			ptmt.setString(3, usermgmtVO.getUsername());
			
			ptmt.executeUpdate();
			//rs = ptmt.executeQuery();
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			//try { rs.close(); } catch (Exception _e) {}
			try { ptmt.close(); } catch (Exception _e) {}
			try { con.close(); } catch (Exception _e) {}			
		}
	}
}
