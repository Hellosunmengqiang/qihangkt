package cn.ybzy.qihangkt.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.pagehelper.PageInfo;

import cn.ybzy.qihangkt.model.Resource;
import cn.ybzy.qihangkt.model.Role;
import cn.ybzy.qihangkt.model.User;
import cn.ybzy.qihangkt.service.RoleService;
import cn.ybzy.qihangkt.service.UserService;
import cn.ybzy.qihangkt.web.AuthMethod;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	/**
	 * 登录页面显示出来的方法
	 */
	@RequestMapping(value="/login.html",method=RequestMethod.GET)
	public String login() {
		return "login";
	}/*
	注册页面
	*/
	@RequestMapping(value="/admin/loging.html",method=RequestMethod.POST)
	public String loging(User user) {
		//System.out.println(user.getEmail());
		String str = "0123456789";
		//abcdefghijklmnopqrstuvwxtzQWERTYUIOPASDFGHJKLZXCVBNM0123456789
		
		Random random = new Random();
		StringBuffer sb = new StringBuffer(4);
		for(int i = 0; i < 6; i++) {
			char b = str.charAt(random.nextInt(str.length()));
			sb.append(b);	
		} 
		try
		{
			HtmlEmail email=new HtmlEmail();
			email.setHostName("smtp.163.com");
			email.setCharset("utf-8");
			email.addTo(user.getEmail());
			email.setFrom("aqiangs@163.com","阿强");
			email.setAuthentication("aqiangs@163.com", "369963sun");
			email.setSubject("登录验证码");
			email.setMsg("大佬，你好！你的邮箱验证码是："+sb.toString());
			email.send();
			
		}
		catch(Exception e) {
			;
		}
		userService.addUser(user);
		return "login";
	}
	
	
	
	@RequestMapping(value="/login.html",method=RequestMethod.POST)
	public String loginPost(String userInfo,String password,HttpSession session) {
		//将参数转发到service层去判断登录成功否
		User user = userService.login(userInfo,password);
		//System.out.println(user);
		session.setAttribute("loginUser", user);
		//判断一下登录成功的用户是不是超级管理员
		List<Role> loginUserRoles = user.getRoles(); //登录用户关联的角色信息
		boolean isadmin = false; //是：true，不是：false
		List<Resource> loginUserRes = null;
		List<String> loginUserPathes = new ArrayList<>();
		for(Role role:loginUserRoles) {
			if("admin".equals(role.getCode())) {
				isadmin = true;
				break; 
			}
			//不是超级管理员的情况下，我们要把登录成功的用户，关联的所有权限标记，取出来
			loginUserRes = role.getResources();
			for(Resource res:loginUserRes) {
				loginUserPathes.add(res.getPath()); //loginUserPathes:存放了当前登录用户所拥有的path路径
			}
		}
		session.setAttribute("isAdmin", isadmin);
		//循环完毕后，loginUserPathes：包括了登录成功的用户，所拥有的所有权限的标记
		session.setAttribute("loginUserAllPath", loginUserPathes);
		
		return "redirect:/admin/admin.html";
	}

}
