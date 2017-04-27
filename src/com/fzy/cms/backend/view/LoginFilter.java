package com.fzy.cms.backend.view;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter {

	private String fP;
	
	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request =(HttpServletRequest) req;
		HttpServletResponse response =(HttpServletResponse) resp;
		
		String requestURI = request.getRequestURI();
		
		//取得/backend及其之后的地址,substring包头不包尾
		String page = requestURI.substring(request.getContextPath().length());
		//取得LoginServlet里保存在session中的Login_Admin
		String loginAdmin = (String) request.getSession().getAttribute("LOGIN_ADMIN");
		
		if(page.matches(fP)){
			if(loginAdmin == null && !page.equals("/backend/login_back.jsp") && !page.equals("/backend/LoginServlet")
					){
				//redirect到login.jsp
				response.sendRedirect(request.getContextPath()+"/backend/login_back.jsp");
				return;
			}
		}
		
		chain.doFilter(req,resp);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		fP = config.getInitParameter("fP");
	}

}
