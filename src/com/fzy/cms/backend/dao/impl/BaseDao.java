package com.fzy.cms.backend.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.fzy.cms.SystemContext;
import com.fzy.cms.backend.VO.PagerVO;
import com.fzy.cms.backend.mode1.Admin;
import com.fzy.cms.backend.mode1.Channel;
import com.fzy.cms.utils.MybatisUtil;

public class BaseDao {
	public void add(Object entity) {
		SqlSession session = MybatisUtil.getSession();

		try {
			// 插入
			session.insert(entity.getClass().getName()+ ".add", entity);

			// 提交事务
			session.commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally {
			// 关闭session
			session.close();
		}
	}
	
	public void update(Object entity){
		SqlSession session = MybatisUtil.getSession();
		try {
			session.update(entity.getClass().getName()+".update", entity);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally{
			session.close();
		}
	}
	
	public void del(Class entityClass, int id) {
		SqlSession session = MybatisUtil.getSession();
		try {
			session.delete(entityClass.getName() + ".del", id);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally {
			session.close();
		}
	}
	
	public void del(Class entityClass, int[] ids) {
		SqlSession session = MybatisUtil.getSession();
		try {
			for (int id : ids) {
				session.delete(entityClass.getName() + ".del", id);
			}
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally {
			session.close();
		}
	}
	
	public void del(Class entityClass, String[] ids) {
		SqlSession session = MybatisUtil.getSession();
		try {
			for (String id : ids) {
				session.delete(entityClass.getName() + ".del", id);
			}
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally {
			session.close();
		}
	}
	
	public Object findById(Class entityClass,int id){
		SqlSession session = MybatisUtil.getSession();
		try {
			return session.selectOne(entityClass.getName()+".findById", id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			session.close();
		}
		return null;
	}
	
	public PagerVO findPaginated(String sqlId,Map params){
		SqlSession session = MybatisUtil.getSession();
		
		params.put("offset", SystemContext.getOffset());
		params.put("pagesize", SystemContext.getPagesize());
		List datas = null;
		int total = 0;
		try {
			datas = session.selectList(sqlId, params);			
			total = (Integer)session.selectOne(sqlId+"-count", params);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			session.close();
		}
		PagerVO pv = new PagerVO();
		pv.setDatas(datas);
		pv.setTotal(total);
		return pv;
	}
	
	public List findAll(Class entityClass) {
		SqlSession session = MybatisUtil.getSession();
		try {
			return session.selectList(entityClass.getName() + ".findAll");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}
}
