package com.fzy.cms.backend.view;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fzy.cms.backend.VO.PagerVO;
import com.fzy.cms.backend.dao.ArticleDao;
import com.fzy.cms.backend.dao.ChannelDao;
import com.fzy.cms.backend.mode1.Article;
import com.fzy.cms.backend.mode1.Channel;
import com.fzy.cms.utils.BeanFactory;
import com.fzy.cms.utils.RequestUtil;

public class ArticleServlet extends BaseServlet{
	private ArticleDao articleDao;
	private ChannelDao channelDao;
	
	//��ѯ����
	protected void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int total = 0;//�ܼ�¼��

		//�ӽ����л�ȡtitle����
		String title = request.getParameter("title");
		
		//��ѯ�����б�
		
		PagerVO pv = articleDao.findArticles(title);
		System.out.println(pv.getDatas());
		
		request.setAttribute("pv", pv);

		//forward��article_list.jsp
		request.getRequestDispatcher("/backend/article/article_list.jsp").forward(request, response);
		}
	//����������µ�jspǰ�ȷ�����������ݿ���ȡ��channel����Ϣ����forward��Ŀ��jsp
	public void addInput(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PagerVO pv = channelDao.findChannels();
		request.setAttribute("channels", pv.getDatas());
		
		//forward��add_article
		request.getRequestDispatcher("article/add_article.jsp").forward(request, response);
	}
	
	//�������
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ���ȣ��ӽ���������µĻ�����Ϣ��������ID������ȵȣ�
		// ��request�л�ȡ����	
		Article a = (Article) RequestUtil.copyParam(Article.class, request);
		a.setCreateTime(new Date());
		//���ݿ����
		articleDao.addArticle(a);
		
		//forward��add_article_success
		request.getRequestDispatcher("article/add_article_success.jsp").forward(request, response);
	}
	
	//�򿪸���ҳ��
	public void updateInput(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//�õ�id
		String id =request.getParameter("id");
		
		//���ݿ����
		Article a = articleDao.findArticleById(Integer.parseInt(id));
		request.setAttribute("article", a);
		
		PagerVO pv = channelDao.findChannels();
		request.setAttribute("channels", pv.getDatas());
		
		//forward������ҳ��.jsp
		request.getRequestDispatcher("/backend/article/update_article.jsp").forward(request,response);
		}
	
	//ִ�и��²���
	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ���ȣ��ӽ���������µĻ�����Ϣ��������ID������ȵȣ�
		Article a = (Article) RequestUtil.copyParam(Article.class, request);
		a.setUpdateTime(new Date());

		
		articleDao.updateArticle(a);
		
		//forward�����³ɹ���ҳ��
		request.getRequestDispatcher("/backend/article/update_article_success.jsp").forward(request, response);
	}

	//ɾ��
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//�õ�id
		String[] ids = request.getParameterValues("id");
		//����
		if (ids == null){
			request.setAttribute("error", "ɾ������idΪ�հ�");
			request.getRequestDispatcher("/backend/common/error.jsp").forward(request, response);
			return;
		}
		//���ݿ����
		articleDao.delArticle(ids);
		
		//for�ֵ���ѯ���µ�
		//�����request�ķ��������ض��򣬻������Ϊ������method=del���ͻ����޵���del
		//request.getRequestDispatcher("/backend/SearchArticleServlet").forward(request, response);
		response.sendRedirect(request.getContextPath()+"/backend/ArticleServlet");
	}
	public void setArticleDao(ArticleDao articleDao) {
		//�Ѵ�BaseServletע�������DaoImpl����this�е�articleDao��Ȼ��Ϳ��Խ������ݿ������
		this.articleDao = articleDao;
	}
	public void setChannelDao(ChannelDao channelDao) {
		this.channelDao = channelDao;
	}
}
