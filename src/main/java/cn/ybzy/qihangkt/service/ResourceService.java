package cn.ybzy.qihangkt.service;

import java.util.List;

import cn.ybzy.qihangkt.model.Resource;

public interface ResourceService extends BaseService<Resource> {

	void initPathes(List<String> pathes);

}
