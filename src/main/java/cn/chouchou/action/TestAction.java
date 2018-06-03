package cn.chouchou.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/test")
public class TestAction {

	
	@RequestMapping("/hello")
	public @ResponseBody String gologin(Model model){
		model.addAttribute("msg", "msg content");
		return  "login";
	}
}
