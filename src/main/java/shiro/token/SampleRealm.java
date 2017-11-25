package shiro.token;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;




import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;

import shiro.entity.UUser;


public class SampleRealm extends AuthorizingRealm{

	@Autowired  
	  
	private SessionDAO sessionDAO;
	
	 /** 
     * 授权 
     */  
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		UUser token = (UUser)SecurityUtils.getSubject().getPrincipal();
		Long userId = token==null?null:token.getId();
		SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
		//根据用户ID查询角色（role），放入到Authorization里。
		System.out.println(userId);
		Set<String> roles = new HashSet<String>();
		roles.add("1");
		roles.add("2");
		info.setRoles(roles);
		//根据用户ID查询权限（permission），放入到Authorization里。
		Set<String> permissions = new HashSet<String>();
		roles.add("3");
		roles.add("4");
		info.setStringPermissions(permissions);
        return info;  
	}

	/**
	 *  认证信息，主要针对用户登录， 
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken arg0) throws AuthenticationException {
		ShiroToken token = (ShiroToken) arg0;
		UUser user;
		boolean b=false;//判断是否已经登录，也可以封装成一个实体类
		/*
		 * 下面应该写成一个方法的，然后再创建一个实体类来保存session里面所有值，比如
		 * object.getString("nickname")
		 */
		 Collection<Session> sessions = sessionDAO.getActiveSessions();  
	        for(Session session:sessions){  
	             String s=String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));//获得session中已经登录用户的名字   
	             JSONObject object=JSONObject.fromObject(s);
	             if(null!=object){
	            	 try{
	            		 if(null!=object.getString("nickname")){
		            		 String loginUsername=object.getString("nickname");
		 	 	            if(token.getUsername().equals(loginUsername)){              
		 	 	                  System.out.println(loginUsername+"已经登录,不能再次登录");
 
		 	 	                b=true;
		 	 	            } 
	            		 }
	            	 }catch (Exception e) {
						e.printStackTrace();
					}            	  
	            	 }            	
	             }
		if(b==false&&token.getUsername().equals("ys")&&token.getPswd().equals("123456")){
			user =new UUser(token.getUsername(),token.getPswd()); 
		}else{
			user=null;
		}
		if(null == user){
			throw new AccountException("帐号或密码不正确！");
		}else{
			//更新登录时间 last login time
			user.setLastLoginTime(new Date());
		}
		return new SimpleAuthenticationInfo(user,user.getPswd(), getName());
	}
	
}
