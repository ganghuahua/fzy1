package com.fzy.cms.backend.dao.impl;

import java.sql.SQLException;

import com.fzy.cms.backend.dao.AdminDao;
import com.fzy.cms.backend.mode1.Admin;
import com.fzy.cms.utils.DBUtils;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

public class AdminDaoImplForMysql implements AdminDao{
	
	public void addAdmin(Admin a){
		
	}
	
	public Admin findAdminByUsername(String username){
		String sql = "select * from t_admin where username = ?";
		Connection conn = (Connection) DBUtils.getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Admin admin = new Admin();
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = (ResultSet) pstmt.executeQuery();
			if(rs.next()){
				admin.setUsername(username);
				admin.setPassword(rs.getString("password"));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(rs);
			DBUtils.close(pstmt);
			DBUtils.close(conn);
		}
		return admin;
	}
}
