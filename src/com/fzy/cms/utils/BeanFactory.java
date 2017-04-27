package com.fzy.cms.utils;

import com.fzy.cms.backend.dao.ArticleDao;

public interface BeanFactory {
	public Object getBean(String name);
}
