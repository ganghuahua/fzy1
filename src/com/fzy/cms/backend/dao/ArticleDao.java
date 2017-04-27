package com.fzy.cms.backend.dao;

import java.util.List;

import com.fzy.cms.backend.VO.PagerVO;
import com.fzy.cms.backend.mode1.Article;
import com.fzy.cms.backend.mode1.Channel;

public interface ArticleDao {
	public void addArticle(Article a);
	public void delArticle(String[] ids);
	public Article findArticleById(int id);
	public void updateArticle(Article a);
	public PagerVO findArticles(String title);
	public PagerVO findArticles(Channel c);
	public List findArticles(Channel c, int max);
	public List findHeadLine(int max); //查询头条
	public List findRecommend(int max);//查询推荐文章
	public PagerVO findRecommend();
	PagerVO findArticlesByKeyword(String keyword);
}
