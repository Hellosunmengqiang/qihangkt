package cn.ybzy.qihangkt.dao;

import java.util.List;

import cn.ybzy.qihangkt.model.Role;

public interface RoleDao extends BaseDao {

	List<Role> selectRelevanceRoles();

	List<Role> selectRolesBySearchPage(String name);

	Role getRoleByName(String name);

}
