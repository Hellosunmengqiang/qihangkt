package cn.ybzy.qihangkt.web;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.ybzy.qihangkt.model.User;

public class AuthIncepertor extends HandlerInterceptorAdapter {

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		String path = "";
		if(handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod(); // 可以拿到当前的url地址，对应的在controller类里的方法
			RequestMapping annotation = method.getAnnotation(RequestMapping.class); // 拿到该方法上的指定的注解
			path = annotation.value()[0];
		}else {
			throw new RuntimeException("输入的url地址不存在！");
		}
		
		//拿到系统中所有要求进行权限控制的方法，对应的权限控制标记
		List<String> allPermissionPathes = (List<String>) request.getServletContext().getAttribute("allPermissionPathes");
		
		//拿到目前登录的用户，他所拥有的所有的权限对应的权限控制标记
		List<String> loginUserAllPath = (List<String>) session.getAttribute("loginUserAllPath");
		
		//拿到目前登录成功的用户的对象
		User loginUser = (User) session.getAttribute("loginUser");
		
		if(loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/login.html");//获取项目的根路径  /qihangkt 
		}else {
			boolean isAdmin = (boolean) session.getAttribute("isAdmin");
			if(!isAdmin  &&  allPermissionPathes.contains(path)) {
				//进行权限控制，要求判断当前登录的用户，有没的权限访问该页面（对应的一个方法）
				if(!loginUserAllPath.contains(path)) {
			        //throw new RuntimeException("你没有访问该页面的权限！");
					request.getRequestDispatcher("/static/jsp/error.jsp").forward(request, response);
				}
			}
		}
		return super.preHandle(request, response, handler); //springmvc放行
	}
	
}
