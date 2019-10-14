package cn.ybzy.qihangkt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.pagehelper.PageInfo;

import cn.ybzy.qihangkt.model.Role;
import cn.ybzy.qihangkt.model.User;
import cn.ybzy.qihangkt.service.RoleService;
import cn.ybzy.qihangkt.service.UserService;
import cn.ybzy.qihangkt.web.AuthClass;
import cn.ybzy.qihangkt.web.AuthMethod;

@AuthClass  //这个注解就是标记，AdminController这个类是需要权限才能访问的
@Controller
public class AdminController {
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	
	@AuthMethod  //这个注解就是标记，admin这个方法是需要权限才能访问的
	@RequestMapping(value="/admin/admin.html",method=RequestMethod.GET)
	public String admin() {
		return "admin/admin";
	}
	
	@AuthMethod
	@RequestMapping(value="/admin/userManager.html",method=RequestMethod.GET)
	public String userManager(Model model,Integer pageNum,Integer pageSize) {
		//获取到所有的角色记录信息,注入到User.jsp视图页面
		List<Role> roles = roleService.selectAll();
		model.addAttribute("allRoles", roles);
		if(pageNum == null || pageNum == 0) pageNum = 1;
		if(pageSize == null || pageSize == 0) pageSize = 10;
		PageInfo<User> users = userService.selectUsersByPager(pageNum, pageSize);
		model.addAttribute("userDatasByPager",users);
		
		return "admin/user";
	}
	
	@AuthMethod
	@RequestMapping(value="/admin/roleManager.html",method=RequestMethod.GET)
	public String roleManager() {
		return "admin/role";
	}
	
	@RequestMapping(value="/admin/resManager.html",method=RequestMethod.GET)
	public String resManager() {
		return "admin/resources";
	}

}
