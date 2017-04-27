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
		
		//�������pagesize�������������session�е�pagesize
		if(request.getParameter("pagesize")!= null){
			request.getSession().setAttribute("pagesize", Integer.parseInt(request.getParameter("pagesize")));
		}
		
		// ϣ����http session�л��pagesize��ֵ�����û�У�������ȱʡֵΪ5
		Integer ps = (Integer) request.getSession().getAttribute("pagesize");
		if (ps == null){
			request.getSession().setAttribute("pagesize", pagesize);
		}else{
			pagesize = ps ;
		}
		
		PagerVO pv = channelDao.findChannels();
		
		request.setAttribute("pv", pv);
		
		//�ض���channel_list
		request.getRequestDispatcher("/backend/channel/channel_list.jsp").forward(request, response);
	}
	
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//��request�л�ȡ����
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		Channel c = new Channel();
		c.setName(name);
		c.setDescription(description);
		channelDao.addChannel(c);
		
		//forward���ɹ�ҳ��
		request.getRequestDispatcher("/backend/channel/add_channel_success.jsp").forward(request, response);
	}
	
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
		channelDao.delChannel(ids);
		
		//for�ֵ���ѯ���µ�
		//�����request�ķ��������ض��򣬻������Ϊ������method=del���ͻ����޵���del
		//request.getRequestDispatcher("/backend/SearchArticleServlet").forward(request, response);
		response.sendRedirect(request.getContextPath()+"/backend/ChannelServlet");
	}
	
	public void updateInput(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//�õ�id
		String id =request.getParameter("id");
		
		//���ݿ����
		Channel c = channelDao.findChannelById(Integer.parseInt(id));
		request.setAttribute("channel", c);
		
		//forward������ҳ��.jsp
		request.getRequestDispatcher("/backend/channel/update_channel.jsp").forward(request,response);
		}
	
	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			//���ȣ��ӽ������Ƶ���Ļ�����Ϣ��������ID�����ơ�������
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String description = request.getParameter("description");
			
			Channel c = new Channel();
			c.setId(Integer.parseInt(id));
			c.setName(name);
			c.setDescription(description);
			channelDao.updateChannel(c);
			
			//forward�����³ɹ���ҳ��
			request.getRequestDispatcher("/backend/channel/update_channel_success.jsp").forward(request, response);

		}	
	
	public void setChannelDao(ChannelDao channelDao) {
		this.channelDao = channelDao;
	}
}
