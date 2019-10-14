package cn.ybzy.qihangkt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ybzy.qihangkt.dao.BaseDao;
import cn.ybzy.qihangkt.dao.RoleDao;
import cn.ybzy.qihangkt.model.Role;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{
	@Autowired
	private RoleDao roleDao;

	@Override
	public BaseDao getBaseDao() {
		return roleDao;
	}

}
