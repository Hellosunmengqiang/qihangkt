package cn.ybzy.qihangkt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ybzy.qihangkt.dao.BaseDao;
import cn.ybzy.qihangkt.dao.ResourceDao;
import cn.ybzy.qihangkt.model.Resource;

@Service("resourceService")
public class ResourceServiceImpl extends BaseServiceImpl<Resource> implements ResourceService{
	@Autowired
	private ResourceDao resourceDao;
	
	@Override
	public BaseDao getBaseDao() {
		return resourceDao;
	}

	@Override
	public void initPathes(List<String> pathes) {
		//有两步考虑
		//1.把数据插入到数据库
		int resCount = 0;
		for(String path:pathes) {
			resCount = resourceDao.selectCountResByPath(path);
			if(resCount == 0) {
				this.add(new Resource(path));
			}
		}
	}

}
