package cn.chouchou.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.chouchou.entity.Supplier;
import cn.chouchou.service.ISupplierService;


@Controller
@RequestMapping("/supplier")
public class SupplierAction {
    
	@Autowired
	private ISupplierService  supplierService;
	
	@RequestMapping("/selectOne")
	public @ResponseBody Supplier   getSupplier(int id){
		return 	supplierService.selectOne(id);
		
	}
}
