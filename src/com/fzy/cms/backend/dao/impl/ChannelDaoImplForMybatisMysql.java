package com.fzy.cms.backend.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.fzy.cms.backend.VO.PagerVO;
import com.fzy.cms.backend.dao.ChannelDao;
import com.fzy.cms.backend.mode1.Channel;
import com.fzy.cms.utils.DBUtils;
import com.fzy.cms.utils.MybatisUtil;

public class ChannelDaoImplForMybatisMysql extends BaseDao implements ChannelDao {
	
	public void addChannel(Channel c) {
		//≤Â»Î∆µµ¿
		add(c);
	}
	public void delChannel(String[] ids) {
		del(Channel.class,ids);
	}
	
	public PagerVO findChannels() {
		Map params = new HashMap();	
		return findPaginated(Channel.class.getName()+".findPaginated",params);
	}
	
	public Channel findChannelById(int id) {
		return (Channel) findById(Channel.class,id);
	}
	
	public void updateChannel(Channel c) {
		update(c);
	}
}
