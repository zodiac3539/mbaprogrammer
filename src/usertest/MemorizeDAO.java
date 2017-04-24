package usertest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MemorizeDAO {
	
	public void deleteCategory(long categoryseq) {
		Connection con = null;
		PreparedStatement ptmt = null;
		PreparedStatement ptmt2 = null;
		
		try {
			DBCon dbcon = new DBCon();
			con = dbcon.getConnection();
			
			con.setAutoCommit(false);
			String sql = "DELETE FROM category WHERE categoryseq=?";
			ptmt = con.prepareStatement(sql);
			ptmt.setLong(1, categoryseq);
			ptmt.executeUpdate();
			
			ptmt2 = con.prepareStatement("DELETE FROM memorize WHERE categoryseq=?");
			ptmt2.setLong(1, categoryseq);
			ptmt2.executeUpdate();
			
			con.commit();
		} catch(Exception ex) {
			ex.printStackTrace();
			try { con.rollback(); } catch(Exception _e) {}
		} finally {
			try { ptmt2.close(); } catch (Exception _e) {}
			try { ptmt.close(); } catch (Exception _e) {}
			try { con.close(); } catch (Exception _e) {}
		}
	}
	
	public void cleanUpOldCategory(long categoryseq) {
		Connection con = null;
		PreparedStatement ptmt = null;
		
		try {
			DBCon dbcon = new DBCon();
			con = dbcon.getConnection();
			
			con.setAutoCommit(false);
			String sql = "DELETE FROM memorize WHERE categoryseq=? AND wheninserted<? AND correct > wrong";
			ptmt = con.prepareStatement(sql);
			ptmt.setLong(1, categoryseq);
			Calendar dNow = new GregorianCalendar();
			dNow.add(Calendar.DATE, -7);
			ptmt.setLong(2, dNow.getTimeInMillis());
			
			ptmt.executeUpdate();
			
			con.commit();
		} catch(Exception ex) {
			ex.printStackTrace();
			try { con.rollback(); } catch(Exception _e) {}
		} finally {

			try { ptmt.close(); } catch (Exception _e) {}
			try { con.close(); } catch (Exception _e) {}
		}
	}
	
	public void insertCategory(CategoryVO categoryVO) {
		Connection con = null;
		PreparedStatement ptmt = null;
		PreparedStatement ptmt2 = null;
		ResultSet rs = null;
		try {
			DBCon dbcon = new DBCon();
			con = dbcon.getConnection();
			
			con.setAutoCommit(false);
			String sql = "SELECT max(categoryseq) FROM category";
			ptmt = con.prepareStatement(sql);
			long categoryseq = 0;
			rs = ptmt.executeQuery();
			if(rs.next()) {
				categoryseq = rs.getLong(1);
			}
			categoryseq = categoryseq + 1;
			
			rs.close();
			StringBuffer strb = new StringBuffer();
			strb.append("INSERT INTO category (categoryseq, userid, categoryname) ");
			strb.append(" VALUES(?, ?, ?)");
			ptmt2 = con.prepareStatement(strb.toString());
			ptmt2.setLong(1, categoryseq);
			ptmt2.setString(2, categoryVO.getUserid());
			ptmt2.setString(3, categoryVO.getCategoryname());
			
			ptmt2.executeUpdate();
			
			con.commit();
		} catch(Exception ex) {
			ex.printStackTrace();
			try { con.rollback(); } catch(Exception _e) {}
		} finally {
			try { ptmt2.close(); } catch (Exception _e) {}
			try { ptmt.close(); } catch (Exception _e) {}
			try { con.close(); } catch (Exception _e) {}
		}
	}
	
	public MemorizeVO plusOneCorrect(long seq) {
		Connection con = null;
		PreparedStatement ptmt = null;
		PreparedStatement ptmt2 = null;
		ResultSet rs = null;

		MemorizeVO ret = new MemorizeVO();
		try {
			DBCon dbcon = new DBCon();
			con = dbcon.getConnection();
			
			String sql = "UPDATE memorize SET correct = correct + 1 WHERE seq=?";
			ptmt = con.prepareStatement(sql);
			ptmt.setLong(1, seq);
			ptmt.executeUpdate();
			
			sql = "SELECT correct, wrong FROM memorize WHERE seq=?";
			ptmt2 = con.prepareStatement(sql);
			ptmt2.setLong(1, seq);
			rs = ptmt2.executeQuery();
			if(rs.next()) {
				ret.setCorrect(rs.getInt(1));
				ret.setWrong(rs.getInt(2));
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try { rs.close(); } catch (Exception _e) {}
			try { ptmt2.close(); } catch (Exception _e) {}
			try { ptmt.close(); } catch (Exception _e) {}
			try { con.close(); } catch (Exception _e) {}
		}
		return ret;
	}
	
	public MemorizeVO plusOneWrong(long seq) {
		Connection con = null;
		PreparedStatement ptmt = null;
		PreparedStatement ptmt2 = null;
		ResultSet rs = null;
		
		MemorizeVO ret = new MemorizeVO();
		try {
			DBCon dbcon = new DBCon();
			con = dbcon.getConnection();
			
			String sql = "UPDATE memorize SET wrong = wrong + 1 WHERE seq=?";
			ptmt = con.prepareStatement(sql);
			ptmt.setLong(1, seq);
			ptmt.executeUpdate();

			sql = "SELECT correct, wrong FROM memorize WHERE seq=?";
			ptmt2 = con.prepareStatement(sql);
			ptmt2.setLong(1, seq);
			rs = ptmt2.executeQuery();
			if(rs.next()) {
				ret.setCorrect(rs.getInt(1));
				ret.setWrong(rs.getInt(2));
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try { rs.close(); } catch (Exception _e) {}
			try { ptmt2.close(); } catch (Exception _e) {}
			try { ptmt.close(); } catch (Exception _e) {}
			try { con.close(); } catch (Exception _e) {}
		}
		return ret;
	}
	
    public List<CategoryVO> getCategoryList(String userid) {
    	List<CategoryVO> ret = new ArrayList<CategoryVO>();
    	
		Connection con = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		try {
			DBCon dbcon = new DBCon();
			con = dbcon.getConnection();
			
			String sql = "SELECT categoryseq, userid, categoryname FROM category WHERE userid=?";
			ptmt = con.prepareStatement(sql);
			ptmt.setString(1, userid);
			
			rs = ptmt.executeQuery();
			
			while(rs.next()) {
				CategoryVO categoryVO = new CategoryVO();
				
				categoryVO.setCategoryseq(rs.getLong("categoryseq"));
				categoryVO.setUserid(rs.getString("userid"));
				categoryVO.setCategoryname(rs.getString("categoryname"));
				
				ret.add(categoryVO);			
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
    
    public void insertMemorize(MemorizeVO memorizeVO) {
    	Connection con = null;
		PreparedStatement ptmt = null;
		PreparedStatement ptmt2 = null;
		try {
			DBCon dbcon = new DBCon();
			con = dbcon.getConnection();

			String sql = "SELECT max(seq) FROM memorize";
			ptmt = con.prepareStatement(sql);
			ResultSet rs = ptmt.executeQuery();
			long seq = 0;
			if(rs.next()) {
				seq = rs.getLong(1);
			} 
			rs.close();
			seq = seq + 1;
			
			//seq BIGINT primary key,
		    //categoryseq BIGINT,
		    //userid VARCHAR(200),
		    //question VARCHAR(255),
		    //answer NVARCHAR(4000),
		    //wheninserted BIGINT
			sql = "INSERT INTO memorize(seq, categoryseq, userid, question, answer, wheninserted, correct, wrong)"
					+ " VALUES(?, ?, ?, ?, ?, ?, 0, 0)";
			ptmt2 = con.prepareStatement(sql);
			ptmt2.setLong(1, seq);
			ptmt2.setLong(2, memorizeVO.getCategoryseq());
			ptmt2.setString(3, memorizeVO.getUserid());
			ptmt2.setString(4, memorizeVO.getQuestion());
			ptmt2.setString(5, memorizeVO.getAnswer());
			ptmt2.setLong(6, System.currentTimeMillis());
			
			ptmt2.executeUpdate();
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try { ptmt.close(); } catch (Exception _e) {}
			try { ptmt2.close(); } catch (Exception _e) {}
			try { con.close(); } catch (Exception _e) {}
		}
    }

    public void updateMemorize(MemorizeVO memorizeVO) {
		Connection con = null;
		PreparedStatement ptmt = null;
		
		try {
			DBCon dbcon = new DBCon();
			con = dbcon.getConnection();
			
			String sql = "UPDATE memorize SET question=?, answer=?, wheninserted=? WHERE seq=?";
			ptmt = con.prepareStatement(sql);
			ptmt.setString(1, memorizeVO.getQuestion());
			ptmt.setString(2, memorizeVO.getAnswer());
			ptmt.setLong(3, System.currentTimeMillis());
			ptmt.setLong(4, memorizeVO.getSeq());
			ptmt.executeUpdate();
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try { ptmt.close(); } catch (Exception _e) {}
			try { con.close(); } catch (Exception _e) {}
		}
		
    }
    
    public void deleteMemorize(long seq) {
		Connection con = null;
		PreparedStatement ptmt = null;
		
		try {
			DBCon dbcon = new DBCon();
			con = dbcon.getConnection();

			String sql = "DELETE FROM memorize WHERE seq=?";
			ptmt = con.prepareStatement(sql);
			ptmt.setLong(1, seq);
			ptmt.executeUpdate();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try { ptmt.close(); } catch (Exception _e) {}
			try { con.close(); } catch (Exception _e) {}
		}

    }
    
    public List<MemorizeVO> getMemorizeList(String userid, long categoryseq) {
    	List<MemorizeVO> ret = new ArrayList<MemorizeVO>();
    	
		Connection con = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		try {
			DBCon dbcon = new DBCon();
			con = dbcon.getConnection();
			
			String sql = "SELECT seq, categoryseq, userid, question, answer, wheninserted, correct, wrong"
					+ " FROM memorize"
					+ " WHERE userid=? AND categoryseq=?"
					+ " ORDER BY seq DESC";
			
			ptmt = con.prepareStatement(sql);
			ptmt.setString(1, userid);
			ptmt.setLong(2, categoryseq);
			
			rs = ptmt.executeQuery();
			
			while(rs.next()) {
				MemorizeVO vo = new MemorizeVO();
				
				vo.setSeq(rs.getLong("seq"));
				vo.setCategoryseq(rs.getLong("categoryseq"));
				vo.setUserid(rs.getString("userid"));
				vo.setQuestion(rs.getString("question"));
				vo.setAnswer(rs.getString("answer"));
				vo.setWheninserted(rs.getLong("wheninserted"));
				
				vo.setCorrect(rs.getInt("correct"));
				vo.setWrong(rs.getInt("wrong"));
				
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
    
    public int getTotalCount(String userid, long categoryseq) {
    	int ret = 0;
    	
		Connection con = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		try {
			DBCon dbcon = new DBCon();
			con = dbcon.getConnection();
			
			String sql = "SELECT COUNT(seq)"
					+ " FROM memorize"
					+ " WHERE userid=? AND categoryseq=?";
			
			ptmt = con.prepareStatement(sql);
			ptmt.setString(1, userid);
			ptmt.setLong(2, categoryseq);

			
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
    
    public MemorizeVO getDataBySeq(long seq) {
    	MemorizeVO ret = new MemorizeVO();
    	
		Connection con = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		try {
			DBCon dbcon = new DBCon();
			con = dbcon.getConnection();
			
			String sql = "SELECT seq, categoryseq, userid, question, answer, wheninserted, correct, wrong"
					+ " FROM memorize"
					+ " WHERE seq=?";
			
			ptmt = con.prepareStatement(sql);
			ptmt.setLong(1, seq);
			
			rs = ptmt.executeQuery();
			
			if(rs.next()) {
				
				ret.setSeq(rs.getLong("seq"));
				ret.setCategoryseq(rs.getLong("categoryseq"));
				ret.setUserid(rs.getString("userid"));
				ret.setQuestion(rs.getString("question"));
				ret.setAnswer(rs.getString("answer"));
				ret.setWheninserted(rs.getLong("wheninserted"));
				
				ret.setCorrect(rs.getInt("correct"));
				ret.setWrong(rs.getInt("wrong"));
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

    public List<MemorizeVO> getMemorizeList(String userid, long categoryseq, int page) {
    	List<MemorizeVO> ret = new ArrayList<MemorizeVO>();
    	
		Connection con = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		try {
			DBCon dbcon = new DBCon();
			con = dbcon.getConnection();
			
			String sql = "SELECT seq, categoryseq, userid, question, answer, wheninserted, correct, wrong"
					+ " FROM memorize"
					+ " WHERE userid=? AND categoryseq=?"
					+ " ORDER BY seq DESC LIMIT ?, 20";
			
			int skip = (page - 1) * 20;
			ptmt = con.prepareStatement(sql);
			ptmt.setString(1, userid);
			ptmt.setLong(2, categoryseq);
			ptmt.setInt(3, skip);
			
			rs = ptmt.executeQuery();
			
			while(rs.next()) {
				MemorizeVO vo = new MemorizeVO();
				
				vo.setSeq(rs.getLong("seq"));
				vo.setCategoryseq(rs.getLong("categoryseq"));
				vo.setUserid(rs.getString("userid"));
				vo.setQuestion(rs.getString("question"));
				vo.setAnswer(rs.getString("answer"));
				vo.setWheninserted(rs.getLong("wheninserted"));
				
				vo.setCorrect(rs.getInt("correct"));
				vo.setWrong(rs.getInt("wrong"));
				
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
