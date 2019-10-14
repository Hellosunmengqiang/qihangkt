package cn.ybzy.qihangkt.web;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.ybzy.qihangkt.service.ResourceService;

public class InitWebServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	//做两个初始化工作
	//1.spring的ico容器的引用变量，拿到
	//2.权限的初始化
	
	private static ApplicationContext applicationContext;
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void init() throws ServletException {
		//1.applicationContext,在这里获取
		ServletContext context =  getServletContext();//这个方法返回web项目中最大的域对象
		applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
		
		try {
			//2.权限控制的初始化
			//packageName实施权限控制的包全名
			String packageName = "cn.ybzy.qihangkt.controller";// cn.ybzy.qihangkt.controller.AdminController
			String packageNamePath = packageName.replace(".", "/"); //  ---> cn/ybzy/sshweb/controller
			//拿到packageNamePath，进一步的获取到对应在服务器上磁盘上的绝对路径
			String packageNameRealPath = this.getClass().getClassLoader().getResource(packageNamePath).getPath();
//			System.out.println("===packageNameRealPath==="+packageNameRealPath);
			File file = new File(packageNameRealPath); //file就是controller在磁盘的这个文件夹
			//遍历这个文件夹你的文件
			String[] classFileNames  = file.list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if(name.endsWith(".class")) {
						return true;
					}
					return false;
				}
			});
			
			//构造出这个class文件的包全名
			List<String> pathes = new ArrayList<>();
			
			for(String classFileName:classFileNames) {
				//.class这个后缀给删除掉
				classFileName = classFileName.substring(0,classFileName.indexOf(".class"));
				//拿到纯粹的类的包全名
				String classAllpackageName = packageName + "." + classFileName;
				//拿到纯粹的类的包全名，可以通过他们获取到对应的类的对象，通过反射
				Class clazz = Class.forName(classAllpackageName);
				//拿到这些controller的对象，获取到在他们身上的注解
				if(!clazz.isAnnotationPresent(AuthClass.class)) continue;
				//剩下的类，都是有@AuthClass这个注解的类，这些类都要进行权限控制的
				//拿到这些类的所有方法
				Method[] methods = clazz.getDeclaredMethods();//包含private
				for(Method method : methods) {
					if(!method.isAnnotationPresent(AuthMethod.class)) continue;
					//都是有@AuthMethod的方法,拿到要保存到permission表里resource字段的值
					RequestMapping reqMapping = method.getAnnotation(RequestMapping.class);
					//System.out.println(reqMapping.value()[0]);
					pathes.add(reqMapping.value()[0]);
				}
			}
			
			//List<String> pathes ： 包含了controller包里，所有被@AuthClass和@AuthMethod共同作用的
			//方法上面的@RequestMapping的value值，都在里面！
			//放到数据表t_resource,获取到ResourceService，通过ResourceService对象调用ResourceDao里slq语句执行添加操作
			ResourceService resourceService = (ResourceService) applicationContext.getBean("resourceService");
			resourceService.initPathes(pathes);
			context.setAttribute("allPermissionPathes", pathes);// 系统中所有的需要权限控制的方法对应的path地址
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
