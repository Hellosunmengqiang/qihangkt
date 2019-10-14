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
import cn.ybzy.qihangkt.web.AuthClass;
import cn.ybzy.qihangkt.web.AuthMethod;

@AuthClass
@Controller
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@AuthMethod
	@RequestMapping(value="/admin/addRole.html",method=RequestMethod.POST)
	public String addUser(Role role) {
		// 这个方法中实现角色的add
	    System.out.println(role);
	    System.out.println(role.getName()+"--"+role.getCode());
		roleService.addRole(role);
		return "redirect:/admin/roleManager.html";
	}
	
	@AuthMethod
	@RequestMapping(value="/admin/roleSearch.html",method=RequestMethod.POST)
	public String searcerRoles(Model model,String roleInfo) {
		//System.out.println(roleInfo);
		int pageNum = 1;
		int pageSize = 10;
		PageInfo<Role> roles= roleService.selectRolesBySearchPage(pageNum, pageSize,roleInfo);
		model.addAttribute("roleDatasByPager", roles);
		//System.out.println(roles.toString());
		return "admin/role";
	}
}
