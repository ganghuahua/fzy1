package com.fzy.cms.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

public class RequestUtil {
	public static Object copyParam (Class entityClass,HttpServletRequest request){
		try {
			Object entity = entityClass.newInstance();
			
			Map allParams = request.getParameterMap();
			
			Set entries = allParams.entrySet();
			for (Iterator iterator = entries.iterator(); iterator.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String name = (String) entry.getKey();
				String[] value = (String[]) entry.getValue();
				
				if(value != null){
					if(value.length==1){
						BeanUtils.copyProperty(entity, name, value[0]);
					}else{
						BeanUtils.copyProperty(entity, name, value);
					}
				}								
			}
			return entity;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
}
