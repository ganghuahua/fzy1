package com.fzy.cms.backend.dao;

import java.util.Random;

import junit.framework.TestCase;

import com.fzy.cms.backend.mode1.Admin;
import com.fzy.cms.utils.PropertiesBeanFactory;

public class AdminDaoTest extends TestCase {

	static PropertiesBeanFactory factory = new PropertiesBeanFactory("beans.properties");
	
	public void testAddAdmin() {
		AdminDao adminDao = (AdminDao) factory.getBean("adminDao");
		
		Admin a = new Admin();
		a.setUsername("�����û�"+new Random().nextInt(9999));
		a.setPassword("��������"+new Random().nextInt(9999));
		
		adminDao.addAdmin(a);
	}
	
	public void testFindAdminByUsername(){		
		AdminDao adminDao = (AdminDao) factory.getBean("adminDao");
		
		Admin a =adminDao.findAdminByUsername("admin");
		System.out.print(a.getId()+","+a.getUsername()+","+a.getPassword());
	}
}
