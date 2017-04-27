package com.fzy.cms.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.fzy.cms.backend.dao.ArticleDao;

public class PropertiesBeanFactory implements BeanFactory {
	Map beans = new HashMap();

	public PropertiesBeanFactory(){
		this("bean.properties");	
	}
	public PropertiesBeanFactory(String configurationFile){
		try {
			Properties props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(configurationFile));
			
			//���������ļ�����ʼ�����е�DAO����
			Set set = props.entrySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String key = (String)entry.getKey(); //DAO������
				String className = (String)entry.getValue(); //ȫ·������
				Class clz = Class.forName(className);
				Object bean = clz.newInstance(); //Ԥ�ȴ����õ�DAO����
				beans.put(key, bean); //����ArticleDAO��AdminDao����Щ����ͳͳput��beans��
			}
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}
	

	public Object getBean(String name){
		//nameΪdao��
		return beans.get(name);
	}
	public ArticleDao getArticleDao() {
		return null;
	}

}
