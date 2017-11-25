package shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

public class RoleFilter extends AccessControlFilter {

	static final String LOGIN_URL = "http://localhost:8080/shiro/index";
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		System.out.println(3);
		String[] arra = (String[])mappedValue;
		
		Subject subject = getSubject(request, response);
		for (String role : arra) {
			if(subject.hasRole("role:" + role)){
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		System.out.println(4);
			Subject subject = getSubject(request, response);  
	        if (subject.getPrincipal() == null) {//表示没有登录，重定向到登录页面  
	            saveRequest(request);  
	            WebUtils.issueRedirect(request, response, LOGIN_URL);  
	        } else { 
	                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);  
	        }  
		return false;
	}

}
