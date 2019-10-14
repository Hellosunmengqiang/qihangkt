package cn.ybzy.qihangkt.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapToEntityTool {
	/**
	 * 缓存类的属性信息
	 */
	private static Map<String, EntityCacheItem> convertItemCache = new HashMap<>();
	/**
	 * map2entry
	 * @return
	 */
	public static <T> T map2entity(Map<Object, Object> map,Class<T> entityClass) {
		//直接尝试从缓存中获取EntityCacheItem对象
		EntityCacheItem entityCacheItem = convertItemCache.get(entityClass.getName());
		if(entityCacheItem == null) {
			entityCacheItem = EntityCacheItem.createEntityCacheItem(entityClass);
			convertItemCache.put(entityClass.getName(), entityCacheItem);
		}
		
		//通过entityClass参数，获取类型里边的属性名称集合
		List<String> fieldNameList = entityCacheItem.getFieldNameList();
		//通过entityClass参数，获取类型里边的set方法的map集合
		Map<String, Method> setMethodMap = entityCacheItem.getSetMethodMap();
		
		Map<Object, Object> targetMap = new HashMap<>();
		String key;
		String key1;
		String key2;
		for(Map.Entry<Object, Object> entry:map.entrySet()) {
			key = entry.getKey().toString();
			while(key.contains("_")) {
				//add_date  a_b_c_d
				key1 = key.substring(0,key.indexOf("_")); //add
				key2 = key.substring(key.indexOf("_")+1); //date
				key = key1 + key2.substring(0,1).toUpperCase()+key2.substring(1); //addDate
			}
			targetMap.put(key, entry.getValue());
		}
		
		T entity = null;
		try {
			entity = entityClass.newInstance();//通过反射的方法，获取这个类型的一个对象
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		Object mapFieldValue=null;
		Method setMethod1 = null;
		Class<?>[] parameterTypes=null;
		for(String fieldName1:fieldNameList) {
			mapFieldValue = targetMap.get(fieldName1);
			//数据库中map集合key：add_date
			//去这个数据的时候用get(key) : addDate
			if(mapFieldValue == null) continue;
			setMethod1 = setMethodMap.get(fieldName1);
			if(setMethod1 == null) continue;
			
			//获取set方法中参数类型的对象
			parameterTypes = setMethod1.getParameterTypes();
			if(parameterTypes == null || parameterTypes.length>1) {
				continue;
			}
			
			if(parameterTypes[0].isAssignableFrom(mapFieldValue.getClass())) {
				//若map传来的属性值的类似和set方法中参数的类型一致
				try {
					setMethod1.invoke(entity, mapFieldValue);//调用是对象的set方法把属性值注入
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}  
			}else {
				System.out.println("不同类型：set方法中的参数类型：" + parameterTypes[0] + "======数据库中查询的结果集中数据类型：" + mapFieldValue.getClass());
			}
		}
		return entity;
	}
	
	
	static class EntityCacheItem{
		
		private EntityCacheItem(){}
		
		private List<String> fieldNameList = new ArrayList<>();
		private Map<String, Method> setMethodMap = new HashMap<>();
		
		public List<String> getFieldNameList() {
			return fieldNameList;
		}
		public Map<String, Method> getSetMethodMap() {
			return setMethodMap;
		}
		
		public void parseEntity(Class<?> entityClass) {
			Field[] fields = entityClass.getDeclaredFields();
			String fieldName;
			String setMethodName;
			Method setMethod = null;
			for(Field field:fields) {
				field.setAccessible(true); //获取去private修饰的属性
				fieldName = field.getName(); //难道属性对象对应的名称
				fieldNameList.add(fieldName);
				setMethodName = "set"+fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				try {
					setMethod = entityClass.getDeclaredMethod(setMethodName, field.getType());
				} catch (Exception e) {
					e.printStackTrace();
				}
				setMethodMap.put(fieldName, setMethod);
			}
		}
		
		public static EntityCacheItem createEntityCacheItem(Class<?> entityClass) {
			EntityCacheItem ci = new EntityCacheItem();
			ci.parseEntity(entityClass);
			return ci;
		}
		
		
		
		
	}

}
