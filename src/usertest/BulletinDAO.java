package usertest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/*
CREATE TABLE bulletin (
	    categoryseq BIGINT primary key,
	    bcategory VARCHAR(255),
	    subject VARCHAR(255),
	    content NVARCHAR(4000),
	    userid VARCHAR(200),
	    filename VARCHAR(200),
	    realname VARCHAR(200)
	);
*/

public class BulletinDAO {
	
    public void insertBulletin(BulletinVO bulletinVO) {
    	Connection con = null;
		PreparedStatement ptmt = null;
		PreparedStatement ptmt2 = null;
		try {
			DBCon dbcon = new DBCon();
			con = dbcon.getConnection();

			String sql = "SELECT max(categoryseq) FROM bulletin";
			ptmt = con.prepareStatement(sql);
			ResultSet rs = ptmt.executeQuery();
			long seq = 0;
			if(rs.next()) {
				seq = rs.getLong(1);
			} 
			rs.close();
			seq = seq + 1;
			
		    //categoryseq BIGINT primary key,
		    //bcategory VARCHAR(255),
		    //subject VARCHAR(255),
		    //content NVARCHAR(4000),
		    //userid VARCHAR(200),
		    //filename VARCHAR(200),
		    //realname VARCHAR(200)
			
			sql = "INSERT INTO memorize(categoryseq, bcategory, subject, content, userid, filename, realname)"
					+ " VALUES(?, ?, ?, ?, ?, ?, ?)";
			ptmt2 = con.prepareStatement(sql);
			
			ptmt2.setLong(1, seq);
			ptmt2.setString(2, bulletinVO.getBcategory());
			ptmt2.setString(3, bulletinVO.getSubject());
			ptmt2.setString(4, bulletinVO.getContent());
			ptmt2.setString(5, bulletinVO.getUserid());
			ptmt2.setString(6, ""); //filename
			ptmt2.setString(7, ""); //realname
			
			ptmt2.executeUpdate();
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try { ptmt.close(); } catch (Exception _e) {}
			try { ptmt2.close(); } catch (Exception _e) {}
			try { con.close(); } catch (Exception _e) {}
		}
    }
	
    public int getTotalCount() {
    	int ret = 0;
    	
		Connection con = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		try {
			DBCon dbcon = new DBCon();
			con = dbcon.getConnection();
			
			String sql = "SELECT COUNT(categoryseq)"
					+ " FROM bulletin";
			//		+ " WHERE userid=? AND categoryseq=?";
			
			ptmt = con.prepareStatement(sql);
			//ptmt.setString(1, userid);
			//ptmt.setLong(2, categoryseq);
			
			rs = ptmt.executeQuery();
			
			if(rs.next()) {
				ret = rs.getInt(1);
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try { rs.close(); } catch (Exception _e) {}
			try { ptmt.close(); } catch (Exception _e) {}
			try { con.close(); } catch (Exception _e) {}
		}
    	
    	
    	return ret;
	}
    
    public BulletinVO getDataBySeq(long categoryseq) {
    	BulletinVO ret = new BulletinVO();
    	
		Connection con = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		try {
			DBCon dbcon = new DBCon();
			con = dbcon.getConnection();
			
			String sql = "SELECT categoryseq, bcategory, subject, content, userid, filename, realname"
					+ " FROM memorize"
					+ " WHERE categoryseq=?";
			//BulletinVO
			ptmt = con.prepareStatement(sql);
			ptmt.setLong(1, categoryseq);
			
			rs = ptmt.executeQuery();
			
			if(rs.next()) {
				ret.setCategoryseq( rs.getLong("categoryseq") );
				ret.setBcategory( rs.getString("bcategory") );
				ret.setSubject( rs.getString("subject") );
				ret.setContent( rs.getString("content") );
				ret.setUserid( rs.getString("userid") );
			
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try { rs.close(); } catch (Exception _e) {}
			try { ptmt.close(); } catch (Exception _e) {}
			try { con.close(); } catch (Exception _e) {}
		}
    	
    	
    	return ret;
    	
    }
	
	public List<BulletinVO> getBulletinList(int page) {
    	List<BulletinVO> ret = new ArrayList<BulletinVO>();
    	
		Connection con = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		try {
			DBCon dbcon = new DBCon();
			con = dbcon.getConnection();
			
			String sql = "SELECT categoryseq, bcategory, subject, userid"
					+ " FROM bulletin"
					//+ " WHERE userid=? AND categoryseq=?"
					+ " ORDER BY categoryseq DESC LIMIT ?, 20";
			
			int skip = (page - 1) * 20;
			ptmt = con.prepareStatement(sql);
			ptmt.setInt(1, skip);
			
			rs = ptmt.executeQuery();
			
			while(rs.next()) {
				BulletinVO vo = new BulletinVO();
				
				vo.setCategoryseq(rs.getLong("categoryseq"));
				vo.setBcategory( rs.getString("bcategory") );
				vo.setSubject(rs.getString("subject"));
				vo.setUserid( rs.getString("userid") );
				
				ret.add(vo);
			}
			
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try { rs.close(); } catch (Exception _e) {}
			try { ptmt.close(); } catch (Exception _e) {}
			try { con.close(); } catch (Exception _e) {}
		}
    	
    	
    	return ret;
    }

}
