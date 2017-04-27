package com.fzy.cms.backend.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.fzy.cms.utils.DBUtils;

public class AddArticleTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sql = "insert into t_article (title) values (?)";
		Connection conn = DBUtils.getConn();
		PreparedStatement pstmt = null;
		//ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "ƒ„¬Ë‡À∞°");
			
			pstmt.executeUpdate();
			
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			DBUtils.rollback(conn);
		} finally{
			//DBUtil.close(rs);
			DBUtils.close(pstmt);
			DBUtils.close(conn);
		}
	}

}
