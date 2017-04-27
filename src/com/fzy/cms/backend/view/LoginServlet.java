package com.fzy.cms.backend.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fzy.cms.backend.dao.AdminDao;
import com.fzy.cms.backend.mode1.Admin;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class LoginServlet extends BaseServlet {
	private AdminDao adminDao;
	
	private int width;
	private int height;
	private int number;
	private String codes;
	
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		width = Integer.parseInt(config.getInitParameter("width"));
		height = Integer.parseInt(config.getInitParameter("height"));
		number = Integer.parseInt(config.getInitParameter("number"));
		codes = config.getInitParameter("codes");
	}

	public void checkcode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("image/jpeg");
		
		
		//创建一张图片
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		
		//创建白色背景
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		
		//画黑边框
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width-1, height-1);
		
		Random random = new Random();
		
		//每个字符占据的宽度
		int x = (width - 1) / number;
		int y = height -4;
		
		StringBuffer sb = new StringBuffer();
		
		//随机生成字符
		for(int i=0; i<number; i++){
			String code = String.valueOf( codes.charAt( random.nextInt(codes.length())) );
			int red = random.nextInt(255);
			int green = random.nextInt(255);
			int blue = random.nextInt(255);
			g.setColor(new Color(red,green,blue));
			
			Font font = new Font("Arial",Font.PLAIN,random(height/2,height));
			g.setFont(font);
			
			g.drawString(code, i * x + 1, y);
			
			sb.append(code);
		}
		
		//将生成的验证码串放到session中，session是放在服务里的
		request.getSession().setAttribute("codes", sb.toString());
		
		//随机生成一些点
		for(int i=0; i<50; i++){
			int red = random.nextInt(255);
			int green = random.nextInt(255);
			int blue = random.nextInt(255);
			g.setColor(new Color(red,green,blue));
			g.drawOval(random.nextInt(width), random.nextInt(height), 1, 1);
		}
		
		OutputStream out = response.getOutputStream();
		//将图片转换为JPEG类型
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image);

		out.flush();
		out.close();
		
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String checkcode = request.getParameter("checkcode");

		String sessioncode= (String)request.getSession().getAttribute("codes");
		//判断验证码是否正确
//		if(!sessioncode.equalsIgnoreCase(checkcode)){
//			request.setAttribute("error", "验证码错误啊");
//			request.getRequestDispatcher("/backend/login_back.jsp").forward(request, response);
//			return;
//		}
		Admin admin = adminDao.findAdminByUsername(username);

		if(admin == null) {
			// forward到long.jsp，并且提示“用户名不存在”
			request.setAttribute("error", "用户【" + username + "】不存在");
			request.getRequestDispatcher("/backend/login_back.jsp").forward(request, response);
			return;
		}
		
		if (!password.equals(admin.getPassword())) {
			// forward到long.jsp，并且提示“用户密码不正确”
			request.setAttribute("error", "用户【" + username + "】的密码不正确，请重试");
			request.getRequestDispatcher("/backend/login_back.jsp").forward(request, response);
			return;
		} 		
		//需要把登录用户的信息存入HTTP SESSION，让服务器端记住这个用户，标示这个用户已经登录
		request.getSession().setAttribute("LOGIN_ADMIN", username);
		
		//到main.jsp
		
		response.sendRedirect(request.getContextPath()+"/backend/main.jsp");
	}

	public void quit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//清空http session
		request.getSession().invalidate();
		
		//转向login_back.jsp
		response.sendRedirect(request.getContextPath()+"/backend/login_back.jsp");
	}
	
	private int random(int min,int max){
		int m = new Random().nextInt(999999) % (max - min);
		return m + min;
	}
	
	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}	
}
