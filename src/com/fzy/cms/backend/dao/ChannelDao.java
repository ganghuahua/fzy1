package com.fzy.cms.backend.dao;

import com.fzy.cms.backend.VO.PagerVO;
import com.fzy.cms.backend.mode1.Channel;

public interface ChannelDao {
	public PagerVO findChannels();
	public void addChannel(Channel c);
	public void delChannel(String[] ids);
	public Channel findChannelById(int id);
	public void updateChannel(Channel c);
}
