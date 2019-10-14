package cn.ybzy.qihangkt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.ybzy.qihangkt.model.User;

public interface UserDao extends BaseDao {
	//不用在写重复的方法，只需要在这写，userdao特有的不重复的方法
	
	//根据用户名获取用户对象
	public User getUserByName(@Param("username") String username);

	public List<User> selectRelevanceUsers();
	
	/**
	 * 通过user的id查询对应的user对象，关联上role对象
	 */
	public User getUserByUid(@Param("uid") Integer uid);

	public List<User> selectUsersBySearchPage(@Param("userInfo") String userInfo);

	public User selectUserByUserInfo(@Param("userInfo") String userInfo);
}
