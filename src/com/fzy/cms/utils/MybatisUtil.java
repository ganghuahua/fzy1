package com.fzy.cms.utils;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisUtil {
	static SqlSessionFactory factory = null;

	static {
		try {
			// ͨ�������ļ���������������
			Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
			factory = new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static SqlSession getSession(){
		return factory.openSession();
	}
}
