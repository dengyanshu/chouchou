package cn.chouchou.service;

import java.io.Serializable;

public interface IBaseService<T> {
	int insert(T t);
	int delete(Serializable id);
	int deleteList(Serializable[] ids);
	int update(T t);
	T selectOne(Serializable id);
}
