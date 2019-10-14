package cn.ybzy.qihangkt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ybzy.qihangkt.dao.BaseDao;
import cn.ybzy.qihangkt.dao.RoleDao;
import cn.ybzy.qihangkt.dao.UserRoleDao;
import cn.ybzy.qihangkt.model.Role;
import cn.ybzy.qihangkt.model.User;


@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Override
	public BaseDao getBaseDao() {
		return roleDao;
	}
	

	@Override
	public List<Role> selectRelevanceRoles() {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public PageInfo<Role> selectRolesBySearchPage(int pageNum, int pageSize, String roleInfo) {

			PageHelper.startPage(pageNum, pageSize);
			List<Role> roleDatas = roleDao.selectRolesBySearchPage("%"+roleInfo+"%");//实现模糊查询
			PageInfo<Role> info = new PageInfo<>(roleDatas);
			return info;
		}

	@Override
	public PageInfo<Role> selectRolesByPager(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Role> roleDatas = roleDao.selectRelevanceRoles();
		PageInfo<Role> info = new PageInfo<>(roleDatas);
		return info;
	}

	@Override
	public void addRole(Role role) {		
		this.addForNotMatch(new Object[] {"name","code"}, new Object[] {role.getName(),role.getCode()});
	    Role r = roleDao.getRoleByName(role.getName());
	    userRoleDao.add("t_role_resource", new Object[] {null,r.getId(),r.getId()});
	}



	
}
