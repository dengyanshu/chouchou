package cn.chouchou.action;

import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/base")
public class BaseAction {
     
	@RequestMapping("/url/{folder}/{item}")
	public String goUrl(@PathParam("folder") String folder,@PathParam("item") String item){
		return "/WEB-INF/"+folder+"/"+item+".jsp";
	}
	
	
	
	@RequestMapping("/jsp/{item}")
	public  String gojsp(@PathParam("item") String item){
		
		//return "/WEB-INF/"+item+".jsp";
		return  item;
	}
}
