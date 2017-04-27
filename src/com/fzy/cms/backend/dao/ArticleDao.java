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
	public List findHeadLine(int max); //��ѯͷ��
	public List findRecommend(int max);//��ѯ�Ƽ�����
	public PagerVO findRecommend();
	PagerVO findArticlesByKeyword(String keyword);
}
