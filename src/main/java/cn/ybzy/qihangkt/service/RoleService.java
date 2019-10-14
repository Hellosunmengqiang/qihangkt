package cn.ybzy.qihangkt.service;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.ybzy.qihangkt.model.Role;

public interface RoleService extends BaseService<Role> {

	List<Role> selectRelevanceRoles();

	PageInfo<Role> selectRolesBySearchPage(int pageNum, int pageSize, String roleInfo);

	PageInfo<Role> selectRolesByPager(int pageNum, int pageSize);

	void addRole(Role role);

}
