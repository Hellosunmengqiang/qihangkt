package cn.ybzy.qihangkt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ybzy.qihangkt.dao.BaseDao;
import cn.ybzy.qihangkt.dao.UserDao;
import cn.ybzy.qihangkt.dao.UserRoleDao;
import cn.ybzy.qihangkt.model.User;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRoleDao userRoleDao;
	

	@Override
	public BaseDao getBaseDao() {
		return userDao;
	}


	@Override
	public void addUser(User user, Integer[] roleIds) {
		//完成添加用户的功能，分两部分
		this.addForNotMatch(new Object[] {"username","password","email","phone","enable","add_date"}
			, new Object[] {user.getUsername(),user.getPassword(),user.getEmail(),user.getPhone(),1,new Date()});
		
		//根据新添加进去的用户的用户名称，查询出刚刚添加进数据可的用户记录，就需要一个方法，根据用户名获取用户对象
		User u = userDao.getUserByName(user.getUsername());
		
		for(Integer rid:roleIds) {
			userRoleDao.add("t_user_role", new Object[] {null,u.getId(),rid});
		}
	}
	@Override
	public void addUser(User user) {
		// TODO 自动生成的方法存根
		this.addForNotMatch(new Object[] {"username","password","email","phone","enable","add_date"}
		, new Object[] {user.getUsername(),user.getPassword(),user.getEmail(),user.getPhone(),1,new Date()});
	
	//根据新添加进去的用户的用户名称，查询出刚刚添加进数据可的用户记录，就需要一个方法，根据用户名获取用户对象
	User u = userDao.getUserByName(user.getUsername());
	userRoleDao.add("t_user_role", new Object[] {null,u.getId(),1});//用户的默认角色rid是1
	}


	@Override
	public List<User> selectRelevanceUsers() {
		return userDao.selectRelevanceUsers();
	}


	@Override
	public PageInfo<User> selectUsersByPager(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<User> userDatas = userDao.selectRelevanceUsers();
		PageInfo<User> info = new PageInfo<>(userDatas);
		return info;
	}

	

	@Override
	public User selectRelUserByUid(Integer uid) {
		User user = userDao.getUserByUid(uid);
		return user;
	}


	@Override
	public void updateUser(User user, Integer[] roleIds) {
		if("".equals(user.getPassword().trim())) user.setPassword(null);
		//修改用户的第一步：
		this.update(user);
		
		//修改用户的第二步，是修改user关联的roles
		//1.删除用户id是user.getId()的所有关联的role
		userRoleDao.deleteByUid(user.getId());
		
		//2.把接收到的roleIds这里的新的用户管理的role的id的数组，从新添加到t_user_role关系表中
		for(Integer rid:roleIds) {
			userRoleDao.add("t_user_role", new Object[] {null,user.getId(),rid});
		}
		
	}


	@Override
	public void deleteByUidRelRole(Integer id) {
		//第一步：把该user的关联关系删除
		userRoleDao.deleteByUid(id);
		//第二步：才删除user本身
		this.delete(id);
		
	}


	@Override
	public void batchDelUsersByIds(Integer[] uidArr) {
		for(Integer id:uidArr) {
			this.deleteByUidRelRole(id);
		}
	}


	@Override
	public PageInfo<User> selectUsersBySearchPage(int pageNum, int pageSize,String userInfo) {
		PageHelper.startPage(pageNum, pageSize);
		List<User> userDatas = userDao.selectUsersBySearchPage("%"+userInfo+"%");
		PageInfo<User> info = new PageInfo<>(userDatas);
		return info;
	}


	@Override
	public User login(String userInfo, String password) {
		//userInfo,查询user表里有没的响应的记录
		User user =  userDao.selectUserByUserInfo(userInfo);
		//System.out.println("===user=====" + user);
		if(user == null) 
			throw new RuntimeException("用户名或密码有错误！");
		//有对应的用户的情况下，进一步的验证
		if(!password.equals(user.getPassword()))
			throw new RuntimeException("用户名或密码有吴！");
		return user;
	}


	
	
	

}
