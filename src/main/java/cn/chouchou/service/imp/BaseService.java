package cn.chouchou.service.imp;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import cn.chouchou.dao.BaseMapper;
import cn.chouchou.dao.SupplierMapper;
import cn.chouchou.service.IBaseService;

public class BaseService<T> implements  IBaseService<T>{
    private  BaseMapper<T>  baseMapper;
	
    @Autowired
    private  SupplierMapper  supplierMapper;
    
    
    @PostConstruct
    public void init()throws Exception{
    	ParameterizedType pt=(ParameterizedType) this.getClass().getGenericSuperclass();
    	Class clazz=(Class) pt.getActualTypeArguments()[0];
    	String localmapper=clazz.getSimpleName().substring(0, 1).toLowerCase()+clazz.getSimpleName().substring(1,clazz.getSimpleName().length())+"Mapper";
        Field basefield=this.getClass().getSuperclass().getDeclaredField("baseMapper");
        Field localField=this.getClass().getSuperclass().getDeclaredField(localmapper);
        basefield.set(this, localField.get(this));		
    }
	
	@Override
	public int insert(T t) {
		// TODO Auto-generated method stub
		return baseMapper.insert(t);
	}

	@Override
	public int delete(Serializable id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteList(Serializable[] ids) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(T t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public T selectOne(Serializable id) {
		// TODO Auto-generated method stub
		return baseMapper.selectOne(id);
	}

}
