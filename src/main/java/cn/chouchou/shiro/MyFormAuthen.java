package cn.chouchou.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class MyFormAuthen extends FormAuthenticationFilter {
	
	/**
	 * 复写这个方法的意义在于 加入验证码的提前判断 如果验证码失败 返回true 
	 * 代表认证失败  
	 * 否则 调用父类继续进行认证  返回认证结果
	 * 
	 * 
	 * shiroLoginFailure  是给shiro 框架在login页面取登陆失败信息用的 
	 * 
	 * */
    @Override
    protected boolean onAccessDenied(ServletRequest request,
    		ServletResponse response) throws Exception {
    	// TODO Auto-generated method stub
    	HttpSession  session=((HttpServletRequest)request).getSession();
        String session_code=(String) session.getAttribute("code");//验证码
    	//从请求中获取页面提交的code
        String page_code=request.getParameter("code");
        
    	if (page_code!=null && session_code!=null) {
			if (!page_code.equals(session_code)) {
				// randomCodeError表示验证码错误 
				request.setAttribute("shiroLoginFailure", "randomCodeError");
				//拒绝访问，不再校验账号和密码 
				return true; 
			}
		}
    	return super.onAccessDenied(request, response);
    }
}
