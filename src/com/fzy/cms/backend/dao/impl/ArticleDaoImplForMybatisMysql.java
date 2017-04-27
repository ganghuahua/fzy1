package com.fzy.cms.backend.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;

import com.fzy.cms.SystemContext;
import com.fzy.cms.backend.VO.PagerVO;
import com.fzy.cms.backend.dao.ArticleDao;
import com.fzy.cms.backend.mode1.Article;
import com.fzy.cms.backend.mode1.Channel;
import com.fzy.cms.utils.MybatisUtil;

public class ArticleDaoImplForMybatisMysql extends BaseDao implements ArticleDao {

	@Override
	public void addArticle(Article a) {
		a.setCreateTime(new Date());
		//得到session
		SqlSession session = MybatisUtil.getSession();
		
		try {
			//插入
			session.insert(Article.class.getName()+".add",a);
			
			//与channel相关的操作
			Set<Channel> channels= a.getChannels();		
			if(channels != null){
				for(Channel c:channels){
					Map params = new HashMap();
					params.put("a", a);
					params.put("c", c);
					session.insert(Article.class.getName()+".insert_channel_article", params);
				}
			}			
			//提交事务
			session.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally{
			//关闭session
			session.close();
		}
	}

	@Override
	public void delArticle(String[] ids) {
		SqlSession session = MybatisUtil.getSession();
		
		try {
			//删除
			for(String id:ids){
				session.delete(Article.class.getName()+".del",Integer.parseInt(id));	
				session.delete(Article.class.getName()+".del_channel_article", Integer.parseInt(id));
			}
			//提交事务
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally{
			//关闭session
			session.close();
		}
	}


	
	@Override
	public Article findArticleById(int id) {
		return (Article) findById(Article.class,id);
	}

	@Override
	public void updateArticle(Article a) {
		SqlSession session = MybatisUtil.getSession();
		try {
			session.update(Article.class.getName()+".update", a);
			session.delete(Article.class.getName()+".del_channel_article", a.getId());

			Set<Channel> channels = a.getChannels();
			if (channels != null) {
				for (Channel c : channels) {
					Map params = new HashMap();
					params.put("a", a);
					params.put("c", c);
					session.insert(Article.class.getName() + ".insert_channel_article", params);
				}
			}
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally{
			session.close();
		}
	}

	@Override
	public PagerVO findArticles(String title) {
		Map params = new HashMap();
		if(title != null){
			params.put("title","%"+title+"%");
		}

		return findPaginated(Article.class.getName()+".findArticleByTitle", params);
	}
	
	public PagerVO findArticles(Channel c) {
		Map params = new HashMap();
		params.put("c", c);
		return findPaginated(Article.class.getName()+".findArticleByChannel", params);
	}
	
	public List findArticles(Channel c, int max) {
		
		Map params = new HashMap();
		params.put("c", c);
		SystemContext.setOffset(0);
		SystemContext.setPagesize(max);
		PagerVO vo = findPaginated(Article.class.getName()+".findArticleByChannel", params);
		return vo.getDatas();
	}
	
	public List findHeadLine(int max) {
		SqlSession session = MybatisUtil.getSession();
		
		try {
			return session.selectList(Article.class.getName()+".findHeadline", max);
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally{
			//关闭session
			session.close();
		}
		return null;
	}
	
	public List findRecommend(int max) {
		SqlSession session = MybatisUtil.getSession();
		
		try {
			return session.selectList(Article.class.getName()+".findRecommend", max);
			
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally{
			//关闭session
			session.close();
		}
		return null;
	}
	
	@Override
	public PagerVO findRecommend() { 
		Map params = new HashMap();
		return findPaginated(Article.class.getName()+".findAllRecommend", params);
	}
	
	@Override
	public PagerVO findArticlesByKeyword(String keyword) {
		SqlSession session = MybatisUtil.getSession();
		
		try {
			
			if(keyword == null || keyword.trim().equals("")){
				return null;
			}
			
			String[] keywords = keyword.split(",| ");
			
			//先找出相关文章的ID列表
			if(keywords != null && keywords.length > 0){
				StringBuffer sb = new StringBuffer();
				for(int i=0; i<keywords.length; i++){
					if(i != 0){
						sb.append(",");
					}
					sb.append("'"+keywords[i]+"'");//"'xxx','xxx2','xxx3'"
				}
				Map params = new HashMap();
				params.put("keywords", sb.toString());
				List articleIds = session.selectList(Article.class.getName()+".findArticlesIdByKeyword", 
						params);
				
				StringBuffer ids = new StringBuffer();
				for(int i=0; i<articleIds.size(); i++){
					if(i != 0){
						ids.append(",");
					}
					ids.append(articleIds.get(i));
				}
				
				params = new HashMap();
				params.put("ids", ids.toString());
				params.put("offset", 0);
				params.put("pagesize", Integer.MAX_VALUE);
				//继续查文章列表
				List articles = session.selectList(Article.class.getName()+".findArticlesByIds", 
						params);
				
				PagerVO pv = new PagerVO();
				pv.setDatas(articles);
				pv.setTotal(articleIds.size());
				return pv;
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally{
			//关闭session
			session.close();
		}
		return null;
	}
}
