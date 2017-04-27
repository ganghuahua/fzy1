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
	
	//查询文章
	protected void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int total = 0;//总记录数

		//从界面中获取title参数
		String title = request.getParameter("title");
		
		//查询文章列表
		
		PagerVO pv = articleDao.findArticles(title);
		System.out.println(pv.getDatas());
		
		request.setAttribute("pv", pv);

		//forward到article_list.jsp
		request.getRequestDispatcher("/backend/article/article_list.jsp").forward(request, response);
		}
	//进入添加文章的jsp前先访问这里，从数据库中取得channel的信息，在forward到目标jsp
	public void addInput(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PagerVO pv = channelDao.findChannels();
		request.setAttribute("channels", pv.getDatas());
		
		//forward到add_article
		request.getRequestDispatcher("article/add_article.jsp").forward(request, response);
	}
	
	//添加文章
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 首先，从界面接收文章的基本信息（包括：ID、标题等等）
		// 从request中获取参数	
		Article a = (Article) RequestUtil.copyParam(Article.class, request);
		a.setCreateTime(new Date());
		//数据库操作
		articleDao.addArticle(a);
		
		//forward到add_article_success
		request.getRequestDispatcher("article/add_article_success.jsp").forward(request, response);
	}
	
	//打开更新页面
	public void updateInput(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//拿到id
		String id =request.getParameter("id");
		
		//数据库操作
		Article a = articleDao.findArticleById(Integer.parseInt(id));
		request.setAttribute("article", a);
		
		PagerVO pv = channelDao.findChannels();
		request.setAttribute("channels", pv.getDatas());
		
		//forward到更新页面.jsp
		request.getRequestDispatcher("/backend/article/update_article.jsp").forward(request,response);
		}
	
	//执行更新操作
	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 首先，从界面接收文章的基本信息（包括：ID、标题等等）
		Article a = (Article) RequestUtil.copyParam(Article.class, request);
		a.setUpdateTime(new Date());

		
		articleDao.updateArticle(a);
		
		//forward到更新成功的页面
		request.getRequestDispatcher("/backend/article/update_article_success.jsp").forward(request, response);
	}

	//删除
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//拿到id
		String[] ids = request.getParameterValues("id");
		//错误
		if (ids == null){
			request.setAttribute("error", "删除错误，id为空啊");
			request.getRequestDispatcher("/backend/common/error.jsp").forward(request, response);
			return;
		}
		//数据库操作
		articleDao.delArticle(ids);
		
		//for沃到查询文章的
		//如果用request的服务器端重定向，会出错。因为还存在method=del，就回无限调用del
		//request.getRequestDispatcher("/backend/SearchArticleServlet").forward(request, response);
		response.sendRedirect(request.getContextPath()+"/backend/ArticleServlet");
	}
	public void setArticleDao(ArticleDao articleDao) {
		//把从BaseServlet注入过来的DaoImpl赋给this中的articleDao。然后就可以进行数据库操作了
		this.articleDao = articleDao;
	}
	public void setChannelDao(ChannelDao channelDao) {
		this.channelDao = channelDao;
	}
}
