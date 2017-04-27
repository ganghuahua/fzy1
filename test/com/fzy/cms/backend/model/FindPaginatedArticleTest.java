package com.fzy.cms.backend.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fzy.cms.utils.DBUtils;

public class FindPaginatedArticleTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//从哪一条记录开始查
		int offset = 20;
		
		//一次查多少记录（一页显示多少记录）
		int pagesize = 5;
		
		String sql = "select * from t_article limit ?,?";
		Connection conn = DBUtils.getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, pagesize);
			
			rs = pstmt.executeQuery();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			while(rs.next()){
				String title = rs.getString("title");
				String content = rs.getString("content");
				Date createTime = rs.getTimestamp("createtime");
				System.out.println(title+","+content);
			}
			
			//conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			//DBUtil.rollback(conn);
		} finally{
			DBUtils.close(rs);
			DBUtils.close(pstmt);
			DBUtils.close(conn);
		}
	}

}