package com.fzy.cms;

public class SystemContext {
	//ThreadLocal
	private static ThreadLocal offset = new ThreadLocal();
	private static ThreadLocal pagesize = new ThreadLocal();
	
	public static void setOffset(int _offset){
		offset.set(_offset);
	}
	public static void setPagesize(int _pagesize){
		pagesize.set(_pagesize);
	}
	
	public static int getOffset(){
		Integer _offset = (Integer) offset.get();
		if (_offset != null) {
			return _offset;
		}
		return 0;
	}
	
	public static int getPagesize(){
		Integer _pagesize = (Integer) pagesize.get();
		if (_pagesize != null) {
			return _pagesize;
		}
		return Integer.MAX_VALUE;
	}
}
