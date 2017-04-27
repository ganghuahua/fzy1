package com.fzy.cms.backend.dao.impl;

import org.apache.ibatis.session.SqlSession;

import com.fzy.cms.backend.dao.AdminDao;
import com.fzy.cms.backend.mode1.Admin;
import com.fzy.cms.utils.MybatisUtil;

public class AdminDaoImplForMybatisMysql extends BaseDao implements AdminDao {

	@Override
	public void addAdmin(Admin admin) {
		add(admin);
	}

	@Override
	public Admin findAdminByUsername(String username) {
		
		Admin admin = null;
		SqlSession session = MybatisUtil.getSession();		
		
		try {
			//插入
			admin = (Admin)session.selectOne(Admin.class.getName()+".findAdminByUsername", username);
			
			//提交事务
			//session.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			//session.rollback();
		} finally{
			//关闭session
			session.close();
		}		
		return admin;
	}
}
