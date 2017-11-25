package shiro.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import shiro.token.ShiroToken;


@Controller
@Scope(value="prototype")
@RequestMapping("/u")
public class LoginController {
	@RequestMapping(value="login",method=RequestMethod.GET)
	public String login(){
		return "index";
	}
	@RequestMapping(value="hi",method=RequestMethod.GET)
	public String hi(){
		return "hi";
	}
	
	@RequestMapping(value="denglu",method=RequestMethod.GET)
	@ResponseBody
	public String submitLogin(String username,String password,HttpServletRequest request){
		String url = null ;
		ShiroToken token = new ShiroToken(username, password);
		SecurityUtils.getSubject().login(token);
		try {
			SavedRequest savedRequest = WebUtils.getSavedRequest(request);
			if(null!=savedRequest){
				url = savedRequest.getRequestUrl();
				//System.out.println("a"+url);
			}
			//如果登录之前没有地址，那么就跳转到首页。
			if(isBlank(url)){
				url = "http://localhost:8080/shiro/u/index";
				//System.out.println("b"+url);
			}
			return url;
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	@RequestMapping(value="index",method=RequestMethod.GET)
	public String index(){
		return "index";
	}
	@RequestMapping(value="logout",method=RequestMethod.GET)
	@ResponseBody
	public void logout(){
		SecurityUtils.getSubject().logout();
	}
	
	
	public static boolean isBlank(Object...objects){
		Boolean result = false ;
		for (Object object : objects) {
			if(null == object || "".equals(object.toString().trim()) 
					|| "null".equals(object.toString().trim())){
				result = true ; 
				break ; 
			}
		}
		return result ; 
	}
}
