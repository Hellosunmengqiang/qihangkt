package cn.ybzy.qihangkt.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.mysql.fabric.xmlrpc.base.Array;

import cn.ybzy.qihangkt.model.Role;
import cn.ybzy.qihangkt.model.User;
import cn.ybzy.qihangkt.service.RoleService;
import cn.ybzy.qihangkt.service.UserService;
import cn.ybzy.qihangkt.web.AuthClass;
import cn.ybzy.qihangkt.web.AuthMethod;

@AuthClass
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	@AuthMethod
	@RequestMapping(value="/admin/addUser.html",method=RequestMethod.POST)
	public String addUser(User user, Integer[] roleIds) {
		// 这个方法中实现用户的add
	
		userService.addUser(user, roleIds);
		return "redirect:/admin/userManager.html";
	}

	
	
	@ResponseBody
	@RequestMapping(value = "/admin/updateUser.html", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String updateUser(Integer id, HttpServletRequest request) {
		// 同过user的id，查询对应的user对象，关联role对象
		User user = userService.selectRelUserByUid(id);
		List<Role> roles = user.getRoles();
		// 获取到所有的role对象信息
		List<Role> allRoles = roleService.selectAll();
		String optStr = "";
		for (Role role : allRoles) {
			if (roles.contains(role)) {
				optStr = optStr + "<option selected value=\"" + role.getId() + "\">" + role.getName() + "</option>\r\n";
			} else {
				optStr = optStr + "<option value=\"" + role.getId() + "\">" + role.getName() + "</option>\r\n";
			}
		}

		String path = request.getContextPath();

		return "<div class=\"modal-header\">\r\n"
				+ "					<button type=\"button\" class=\"close\" data-dismiss=\"modal\">\r\n"
				+ "						<span>&times;</span>\r\n" + "					</button>\r\n"
				+ "					<h4 class=\"modal-title\" id=\"myModalLabel\">编辑用户</h4>\r\n"
				+ "				</div>\r\n" + "				<div class=\"modal-body\">\r\n"
				+ "					<form id=\"updateUserForm\" action=\"" + path
				+ "/admin/updateUser.html\" method=\"post\">\r\n"
				+ "                       <input type='hidden' name='id' value='" + user.getId() + "'/>"
				+ "						<div class=\"form-group\">\r\n"
				+ "							<label>用户名：</label> <input type=\"text\" disabled name=\"username\" class=\"form-control\" value=\""
				+ user.getUsername() + "\">\r\n" + "						</div>\r\n"
				+ "						<div class=\"form-group\">\r\n"
				+ "							<label>密码（留空表示不修改密码）：</label> <input type=\"password\" name=\"password\" class=\"form-control\" value=\"\">\r\n"
				+ "						</div>\r\n" + "						<div class=\"form-group\">\r\n"
				+ "							<label>关联的角色：</label> \r\n"
				+ "							<select name=\"roleIds\" class=\"selectpicker form-control\" multiple>\r\n"
				+ optStr + "							</select>\r\n" + "						</div>\r\n"
				+ "						<div class=\"form-group\">\r\n"
				+ "							<label>手机号：</label> <input type=\"text\" name=\"phone\" class=\"form-control\" value=\""
				+ user.getPhone() + "\">\r\n" + "						</div>\r\n"
				+ "						<div class=\"form-group\">\r\n"
				+ "							<label>邮箱：</label> <input type=\"text\" name=\"email\" class=\"form-control\" value=\""
				+ user.getEmail() + "\">\r\n" + "						</div>\r\n" + "					</form>\r\n"
				+ "				</div>\r\n" + "				<div class=\"modal-footer\">\r\n"
				+ "					<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">关闭</button>\r\n"
				+ "					<button onclick=\"updateUserFormSubmint()\" type=\"button\" class=\"btn btn-primary\">编辑用户</button>\r\n"
				+ "				</div>";
	}

	@AuthMethod
	@RequestMapping(value="/admin/updateUser.html",method=RequestMethod.POST)
	public String updateUser(User user, Integer[] roleIds) {
		userService.updateUser(user, roleIds);
		return "redirect:/admin/userManager.html";
	}

	@AuthMethod
	@RequestMapping(value="/admin/delUser.html",method=RequestMethod.GET)
	public String delUserById(Integer id) {
		userService.deleteByUidRelRole(id);
		return "redirect:/admin/userManager.html";
	}

	@AuthMethod
	@ResponseBody
	@RequestMapping(value="/admin/batchDelUsers.html",method=RequestMethod.POST)
	public String batchDelUsers(String uid) {
		// 真的实现删除用户的功能的代码  
		uid = uid.substring(1, uid.length() - 1);
		uid = uid.replaceAll("\"", "");
		String[] uidStrArr = uid.split(",");
		Integer[] uidArr = new Integer[uidStrArr.length];
		for (int i = 0; i < uidStrArr.length; i++) {
			uidArr[i] = Integer.parseInt(uidStrArr[i]);
		}
		userService.batchDelUsersByIds(uidArr);
		return "success";
	}

	@AuthMethod
	@RequestMapping(value="/admin/userSearch.html",method=RequestMethod.POST)
	public String searcerUsers(Model model,String userInfo) {
		// 获取到所有的角色记录信息,注入到User.jsp视图页面
		List<Role> roles = roleService.selectAll();
		model.addAttribute("allRoles", roles);
		int pageNum = 1;
		int pageSize = 10;
		PageInfo<User> users = userService.selectUsersBySearchPage(pageNum, pageSize,userInfo);
		model.addAttribute("userDatasByPager", users);
		return "admin/user";
	}

}
