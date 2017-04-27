package com.fzy.cms.backend.dao;

import com.fzy.cms.backend.mode1.Admin;

public interface AdminDao {
	public void addAdmin(Admin a);
	public Admin findAdminByUsername(String username);
}
