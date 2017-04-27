package com.fzy.cms.backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.fzy.cms.backend.VO.PagerVO;
import com.fzy.cms.backend.mode1.Article;
import com.fzy.cms.backend.mode1.Channel;
import com.fzy.cms.utils.BeanFactory;
import com.fzy.cms.utils.DBUtils;
import com.fzy.cms.utils.PropertiesBeanFactory;

import junit.framework.TestCase;

public class ArticleDaoTest extends TestCase{
	
	static PropertiesBeanFactory factory = new PropertiesBeanFactory("beans.properties");
	
	public void testAddArticle(){
		ArticleDao articleDao = (ArticleDao) factory.getBean("articleDao");
		Random r = new Random();
		
		Set channels = new HashSet();
		Channel c = new Channel();
		Channel c1 = new Channel();
		c.setId(1);
		c1.setId(2);
		channels.add(c);
		channels.add(c1);
		
		Article a = new Article();
		a.setTitle("测试文章"+r.nextInt(99999));
		a.setContent("测试文章的内容"+r.nextInt(99999));
		a.setHeadline(true);
		a.setType("原创");
		a.setChannels(channels);
		
		articleDao.addArticle(a);
		
	}
	
	//测试把文章放到某个频道下面
	public void testAddArticle02(){
		
		BeanFactory factory = new PropertiesBeanFactory("beans.properties");
		ArticleDao articleDao = (ArticleDao)factory.getBean("articleDao");
		
		Random r = new Random();
		for (int i = 0; i < 5; i++) {
			Article a = new Article();
			a.setTitle("测试文章" + r.nextInt(99999));
			a.setContent("测试文章的内容" + r.nextInt(99999));
			a.setHeadline(true);
			a.setType("原创");

			// 设置文章属于哪些频道
			Set channels = new HashSet();
			Channel c = new Channel();
			c.setId(1);
			channels.add(c);

			a.setChannels(channels);

			articleDao.addArticle(a);
		}
	}	
	
	//测试把文章放到多个频道下面
	public void testAddArticle03(){
		
		BeanFactory factory = new PropertiesBeanFactory("beans.properties");
		ArticleDao articleDao = (ArticleDao)factory.getBean("articleDao");
		
		Random r = new Random();
		
		Article a = new Article();
		a.setTitle("测试文章"+r.nextInt(99999));
		a.setContent("测试文章的内容"+r.nextInt(99999));
		a.setHeadline(true);
		a.setType("原创");
		
		//设置文章属于哪些频道
		Set channels = new HashSet();
		Channel c = new Channel();
		c.setId(1);
		channels.add(c);
		
		Channel c2 = new Channel();
		c2.setId(3);
		channels.add(c2);
		
		a.setChannels(channels);
		
		articleDao.addArticle(a);
		
	}		
	
	//删t_article里的数据
	public void testDelArticle(){
		ArticleDao articleDao = (ArticleDao) factory.getBean("articleDao");
		String[] ids = {"14","15","16","17"};
		articleDao.delArticle(ids);
	}
	
	//删t_article的同时也删除t_channel_article中对应的文章
	public void testDelArticle02() {
		String sql = "delete from t_article where id = ?";
		String sqlForChannel = "delete from t_channel_article where articleId = ?";
		Connection conn = DBUtils.getConn();
		PreparedStatement pstmt = null;
		PreparedStatement pstmtForChannel = null;
		try {
			pstmt = conn.prepareStatement(sql);
			// pstmt.setInt(1,123);
			pstmt.setInt(1, 8);
			pstmt.executeUpdate();
			
			pstmtForChannel = conn.prepareStatement(sqlForChannel);
			pstmtForChannel.setInt(1, 8);
			pstmtForChannel.executeUpdate();
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			DBUtils.rollback(conn);
		} finally {
			DBUtils.close(pstmt);
			DBUtils.close(conn);
		}
	}
	
	public void testFindArticleById(){
		Article a = null;
		
		ArticleDao articleDao = (ArticleDao) factory.getBean("articleDao");
		a = articleDao.findArticleById(19);
		System.out.println(a.getTitle());
	}
	
//	public void testFindArticles(){
//		ArticleDao articleDao = (ArticleDao) factory.getBean("articleDao");
//		
//		PagerVO pv = articleDao.findArticles("测试文章", 0, 5);
//		List datas = pv.getDatas();
//		int total = pv.getTotal();
//		for (Iterator iterator = datas.iterator(); iterator.hasNext();) {
//			Article article = (Article) iterator.next();
//			System.out.println(article.getTitle());
//		}
//		System.out.println("文章总数="+total);
//	}
//	
//	public void testFindChannelArticles(){
//		ArticleDao articleDao = (ArticleDao) factory.getBean("articleDao");
//		Channel c = new Channel();
//		c.setId(1);
//		
//		PagerVO pv = articleDao.findArticles(c, 0, 5);
//		
//		List datas = pv.getDatas();
//		int total = pv.getTotal();
//		for (Iterator iterator = datas.iterator(); iterator.hasNext();) {
//			Article article = (Article) iterator.next();
//			System.out.println(article.getId());
//		}
//		System.out.println("频道1的文章总数="+total);
//	}
}
