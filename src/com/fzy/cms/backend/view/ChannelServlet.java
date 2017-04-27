package com.fzy.cms.backend.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fzy.cms.backend.VO.PagerVO;
import com.fzy.cms.backend.dao.ArticleDao;
import com.fzy.cms.backend.dao.ChannelDao;
import com.fzy.cms.backend.mode1.Article;
import com.fzy.cms.backend.mode1.Channel;
import com.fzy.cms.utils.BeanFactory;


public class ChannelServlet extends BaseServlet {
	private ChannelDao channelDao;
	
	
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int offset = 0;
		int pagesize = 5;

		try {
			offset = Integer.parseInt(request.getParameter("pager.offset"));
		} catch (Exception e) {
		}
		
		//如果有有pagesize传过来，则更新session中的pagesize
		if(request.getParameter("pagesize")!= null){
			request.getSession().setAttribute("pagesize", Integer.parseInt(request.getParameter("pagesize")));
		}
		
		// 希望从http session中获得pagesize的值，如果没有，则设置缺省值为5
		Integer ps = (Integer) request.getSession().getAttribute("pagesize");
		if (ps == null){
			request.getSession().setAttribute("pagesize", pagesize);
		}else{
			pagesize = ps ;
		}
		
		PagerVO pv = channelDao.findChannels();
		
		request.setAttribute("pv", pv);
		
		//重定向到channel_list
		request.getRequestDispatcher("/backend/channel/channel_list.jsp").forward(request, response);
	}
	
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//从request中获取参数
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		Channel c = new Channel();
		c.setName(name);
		c.setDescription(description);
		channelDao.addChannel(c);
		
		//forward到成功页面
		request.getRequestDispatcher("/backend/channel/add_channel_success.jsp").forward(request, response);
	}
	
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
		channelDao.delChannel(ids);
		
		//for沃到查询文章的
		//如果用request的服务器端重定向，会出错。因为还存在method=del，就回无限调用del
		//request.getRequestDispatcher("/backend/SearchArticleServlet").forward(request, response);
		response.sendRedirect(request.getContextPath()+"/backend/ChannelServlet");
	}
	
	public void updateInput(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//拿到id
		String id =request.getParameter("id");
		
		//数据库操作
		Channel c = channelDao.findChannelById(Integer.parseInt(id));
		request.setAttribute("channel", c);
		
		//forward到更新页面.jsp
		request.getRequestDispatcher("/backend/channel/update_channel.jsp").forward(request,response);
		}
	
	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			//首先，从界面接收频道的基本信息（包括：ID、名称、描述）
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String description = request.getParameter("description");
			
			Channel c = new Channel();
			c.setId(Integer.parseInt(id));
			c.setName(name);
			c.setDescription(description);
			channelDao.updateChannel(c);
			
			//forward到更新成功的页面
			request.getRequestDispatcher("/backend/channel/update_channel_success.jsp").forward(request, response);

		}	
	
	public void setChannelDao(ChannelDao channelDao) {
		this.channelDao = channelDao;
	}
}
