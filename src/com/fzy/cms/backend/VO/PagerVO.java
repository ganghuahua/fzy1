package com.fzy.cms.backend.VO;

import java.util.List;

public class PagerVO {
	private int total;
	private List datas;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List getDatas() {
		return datas;
	}
	public void setDatas(List datas) {
		this.datas = datas;
	}	
}
