package com.fzy.cms.backend.view;

import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.beanutils.ConvertUtils;

import com.fzy.cms.utils.BeanFactory;
import com.fzy.cms.utils.ChannelsSetConverter;
import com.fzy.cms.utils.PropertiesBeanFactory;

public class InitBeanFactoryServlet extends HttpServlet {
	public static final String INIT_FACTORY_NAME = "_my_bean_factory";
	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.print("��ʼ���ɹ�");
		BeanFactory factory = null;
		//configLocation = beans.properties
		String configLocation = config.getInitParameter("configLocation");//beans.properties
		if(configLocation == null){
			factory = new PropertiesBeanFactory();
		}else{
			factory = new PropertiesBeanFactory(configLocation);
		}
		config.getServletContext().setAttribute(INIT_FACTORY_NAME, factory);
		
		//���article��set���͵�ת����ʼ��
		ConvertUtils.register(new ChannelsSetConverter(), Set.class);
	}

}
