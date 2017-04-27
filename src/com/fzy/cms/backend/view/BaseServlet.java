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
	//这里报错 空指针 可能返回了一个空值没办法转换

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*把InitBeanFactoryServlet放入ServletContext的factory（factory = new PropertiesBeanFactory(#);）
		拿出来放进this的factory里*/
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
				
				//约定：setters方法所决定的属性（property）名。
				//propertyName为articleDao，channelDao这些
				//getBean拿到所需要的DaoImpl
				Object bean = factory.getBean(propertyName);
				
				try {
					//把拿到的DaoImpl（实现了ArticleDao接口的那些类）注入到servlet中
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
		
		//set相应的offset和pagesize到常量池中
		SystemContext.setOffset(getOffset(request));
		SystemContext.setPagesize(getPagesize(request));
		
		//执行父类的职责：根据请求是GET还是POST方法，调用doGet或doPost！
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
	
	//根据传过来的method判断调用拿个方法
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

		//默认方法 什么也不做 由子类重写
	}	
	
	protected int getOffset(HttpServletRequest request){
		int offset = 0;
		// 希望从request中获得offset参数
		try {
			offset = Integer.parseInt(request.getParameter("pager.offset"));
		} catch (Exception ignore) {
		}
		return offset;
	}
	protected int getPagesize(HttpServletRequest request){
		int pagesize = 5;
		//是否有pagesize参数从request中传过来，有则更新session中的pagesize
		if(request.getParameter("pagesize")!=null){
			request.getSession().setAttribute("pagesize", Integer.parseInt(request.getParameter("pagesize")));
			Integer b = Integer.parseInt(request.getParameter("pagesize"));
		}
		
		//如果session中的pagesize没有，就把pagesize放到http session中 因为其他频道也要用到pagesize
		Integer ps = (Integer)request.getSession().getAttribute("pagesize");
		if(ps == null){
			request.getSession().setAttribute("pagesize", pagesize);
		}else{
			pagesize = ps;
		}

		return pagesize;
	}
}
