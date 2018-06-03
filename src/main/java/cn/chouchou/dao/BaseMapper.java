package cn.chouchou.dao;

import java.io.Serializable;

public interface BaseMapper<T> {
	
	int insert(T t);
	int delete(Serializable id);
	int deleteList(Serializable[] ids);
	int update(T t);
	T selectOne(Serializable id);

}
