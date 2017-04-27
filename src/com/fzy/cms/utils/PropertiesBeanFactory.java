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
			
			//根据配置文件，初始化所有的DAO对象
			Set set = props.entrySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String key = (String)entry.getKey(); //DAO的名称
				String className = (String)entry.getValue(); //全路径类名
				Class clz = Class.forName(className);
				Object bean = clz.newInstance(); //预先创建好的DAO对象
				beans.put(key, bean); //缓存ArticleDAO啊AdminDao啊这些对象统统put进beans里
			}
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}
	

	public Object getBean(String name){
		//name为dao名
		return beans.get(name);
	}
	public ArticleDao getArticleDao() {
		return null;
	}

}
