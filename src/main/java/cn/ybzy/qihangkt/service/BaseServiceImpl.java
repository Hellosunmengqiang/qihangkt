package cn.ybzy.qihangkt.service;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.ybzy.qihangkt.dao.BaseDao;
import cn.ybzy.qihangkt.model.User;
import cn.ybzy.qihangkt.tools.MapToEntityTool;

/**
 * BaseServiceImpl做成一个抽象类
 * @author E580
 *
 * @param <T>
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {
	
	/**
	 * 获取baseDao对象的解决方法
	 * @return
	 */
	public abstract BaseDao getBaseDao();
	
	/**
	 * 在增删改查方法中需要知道T到底是什么Class
	 */
	public Class<?> clazz;
	public String tableName;
	
	public BaseServiceImpl() {
		clazz = (Class<?>) ((((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments())[0]);
		//获取数据表的名字
	    tableName = "t_" + clazz.getSimpleName().toLowerCase();
	}
	
	@Override
	public void add(T t) {
		List<Object> list = new ArrayList<>();
		for(Field field:t.getClass().getDeclaredFields()) {
			field.setAccessible(true);// 打开获取private修饰的属性权限
			try {
				list.add(field.get(t));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		getBaseDao().add(tableName, list.toArray());
	}
	
	

	@Override
	public void addForNotMatch(Object[] fieldNames, Object[] fieldVales) {
		getBaseDao().addForNotMatch(tableName,fieldNames,fieldVales);
	}

	@Override
	public void delete(int id) {
		getBaseDao().delete(tableName, id);
	}

	@Override
	public void update(T t) {
		List<Object> list = new ArrayList<>();
		int id = 0;
		for(Field field:t.getClass().getDeclaredFields()) {
			field.setAccessible(true);// 打开获取private修饰的属性权限
			try {
				if(field.get(t) == null) {
					continue;
				}
				if("id".equals(field.getName())) {
					id = (int) field.get(t);
					continue;
				}
				//剩下的字段，才是要修改到数据表里的字段值
				//构造update中的set后面的   “字段名=修改后的字段值”
				list.add(field.getName() + "='"+field.get(t)+"'");
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		getBaseDao().update(tableName, id, list.toArray());
	}

	@SuppressWarnings("unchecked")
	@Override
	public T select(int id) {
		Map<Object, Object> rsMap = getBaseDao().select(tableName, id);
		//我们需要把Map转型T
		return (T) MapToEntityTool.map2entity(rsMap, clazz);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<T> selectAll() {
		List<Map<Object, Object>> rsList = getBaseDao().selectAll(tableName);
		List<T> list = new ArrayList<>();
		T t = null;
		for(Map<Object, Object> map:rsList) {
			t = (T) MapToEntityTool.map2entity(map, clazz);
			list.add(t);
		}
		return list;
	}



}
