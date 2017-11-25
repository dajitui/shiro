package shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;

import shiro.entity.UUser;


public class LoginFilter  extends AccessControlFilter {
	final static Class<LoginFilter> CLASS = LoginFilter.class;
	//判断是否未登录
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		
		UUser token = (UUser)SecurityUtils.getSubject().getPrincipal();
		
		if(null != token || isLoginRequest(request, response)){
			System.out.println("已经登录");
            return Boolean.TRUE;
        } 
		return Boolean.FALSE ;
            
	}
//访问失败后是否对其判断
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
			throws Exception {
		//保存Request和Response 到登录后的链接
		saveRequestAndRedirectToLogin(request, response);
		return Boolean.FALSE ;
	}
	

}
