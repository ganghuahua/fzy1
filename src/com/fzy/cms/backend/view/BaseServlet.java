package com.fzy.cms.backend.view;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fzy.cms.SystemContext;
import com.fzy.cms.utils.BeanFactory;
import com.fzy.cms.utils.PropertiesBeanFactory;

public class BaseServlet extends HttpServlet {
	//���ﱨ�� ��ָ�� ���ܷ�����һ����ֵû�취ת��

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*��InitBeanFactoryServlet����ServletContext��factory��factory = new PropertiesBeanFactory(#);��
		�ó����Ž�this��factory��*/
		BeanFactory factory = (BeanFactory)getServletContext().getAttribute(InitBeanFactoryServlet.INIT_FACTORY_NAME);
		
		Method[] methods = this.getClass().getMethods();

		for (Method m : methods) {
			if (m.getName().startsWith("set")) {
				
				//ArticleDao
				String propertyName = m.getName().substring(3);
				
				StringBuffer sb = new StringBuffer(propertyName);
				sb.replace(0, 1, (propertyName.charAt(0)+"").toLowerCase());
				
				//articleDao
				propertyName = sb.toString();
				
				//Լ����setters���������������ԣ�property������
				//propertyNameΪarticleDao��channelDao��Щ
				//getBean�õ�����Ҫ��DaoImpl
				Object bean = factory.getBean(propertyName);
				
				try {
					//���õ���DaoImpl��ʵ����ArticleDao�ӿڵ���Щ�ࣩע�뵽servlet��
					m.invoke(this, bean);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		
		//set��Ӧ��offset��pagesize����������
		SystemContext.setOffset(getOffset(request));
		SystemContext.setPagesize(getPagesize(request));
		
		//ִ�и����ְ�𣺸���������GET����POST����������doGet��doPost��
		super.service(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(req,resp);
	}
	
	//���ݴ�������method�жϵ����ø�����
	protected void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String method = request.getParameter("method");
		
		if(method == null || method.trim().equals("")){
			execute(request,response);
		}else{
			Method m;
			try {
				m = this.getClass().getMethod(method, HttpServletRequest.class,HttpServletResponse.class);
				m.invoke(this, request,response);
			}catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	protected void execute(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		//Ĭ�Ϸ��� ʲôҲ���� ��������д
	}	
	
	protected int getOffset(HttpServletRequest request){
		int offset = 0;
		// ϣ����request�л��offset����
		try {
			offset = Integer.parseInt(request.getParameter("pager.offset"));
		} catch (Exception ignore) {
		}
		return offset;
	}
	protected int getPagesize(HttpServletRequest request){
		int pagesize = 5;
		//�Ƿ���pagesize������request�д��������������session�е�pagesize
		if(request.getParameter("pagesize")!=null){
			request.getSession().setAttribute("pagesize", Integer.parseInt(request.getParameter("pagesize")));
			Integer b = Integer.parseInt(request.getParameter("pagesize"));
		}
		
		//���session�е�pagesizeû�У��Ͱ�pagesize�ŵ�http session�� ��Ϊ����Ƶ��ҲҪ�õ�pagesize
		Integer ps = (Integer)request.getSession().getAttribute("pagesize");
		if(ps == null){
			request.getSession().setAttribute("pagesize", pagesize);
		}else{
			pagesize = ps;
		}

		return pagesize;
	}
}
