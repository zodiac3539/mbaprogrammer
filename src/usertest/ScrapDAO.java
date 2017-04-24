package usertest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ScrapDAO {
	public List<ScrapVO> getRecentFive() {
		List<ScrapVO> ret = new ArrayList<ScrapVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		
		try {
			DBCon ctx = new DBCon();
			con = ctx.getConnection();
			
			pstmt = con.prepareStatement("SELECT max(seq) FROM currencyxe");
			rs = pstmt.executeQuery();
			long seq = 1;
			
			if(rs.next()) {
				seq = rs.getLong(1);
			}
			rs.close();
			
			long end = seq;
			long start = end - 5;
			if(start < 1) start = 1;
			
			pstmt2 = con.prepareStatement("SELECT seq, dstring, usdkrw, eurkrw FROM currencyxe WHERE seq between ? and ?");
			pstmt2.setLong(1, start);
			pstmt2.setLong(2, end);
			
			rs = pstmt2.executeQuery();
			
			while(rs.next()) {
				ScrapVO vo = new ScrapVO();
				vo.setSeq(rs.getLong(1));
				vo.setDtime(rs.getString(2));
				vo.setUsdkrw(rs.getDouble(3));
				vo.setUsdeur(rs.getDouble(4));
				ret.add(vo);
			}
			
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {			
			try { rs.close(); } catch(Exception _e) {}
			try { pstmt.close(); } catch(Exception _e) {}
			try { pstmt2.close(); } catch(Exception _e) {}
			try { con.close(); } catch(Exception _e) {}
		}
		
		return ret;
	}
}
