package shiro;

import java.util.Arrays;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

public class RoleTest {
	
	@Test  
	public void testHasRole() {  
	    login("classpath:shiro-role.ini", "zhang", "123");  
	    //判断拥有角色：role1  
	    System.out.println(RoleTest.getsubject().hasRole("role1"));  
	    //判断拥有角色：role1 and role2  
	    System.out.println(getsubject().hasAllRoles(Arrays.asList("role1", "role2"))); 
	    //判断拥有角色：role1 and role2 and !role3  
	    boolean[] result = getsubject().hasRoles(Arrays.asList("role1", "role2", "role3"));  
	    System.out.println( result[0]);  
	    System.out.println(result[1]);  
	    System.out.println( result[2]);  
	} 
	
	@Test
	public void testperssion(){
		 login("classpath:shiro-role.ini", "zhang", "123");  
		try{  
			//getsubject().checkPermission("user:add");  
			System.out.println(SecurityUtils.getSubject().getPrincipal().toString());
			if(getsubject().isPermitted("user:create")){
				System.out.println("当前Subject 包含user：create权限");  
			}
			else{
				System.out.println("当前Subject 不包含user:create权限");  
			}
           getsubject().checkPermission("user:delete");  
            System.out.println("当前Subject 包含user.delete权限");  
        }catch(UnauthorizedException e){  
            System.out.println("异常抛出："+e.getMessage());  
        } 
	}
	
	public Subject login(String iniPath,String username,String password){  
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager    
       Factory<org.apache.shiro.mgt.SecurityManager> factory =    
               new IniSecurityManagerFactory(iniPath);    
       //2、得到SecurityManager实例 并绑定给SecurityUtils    
       org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();    
       SecurityUtils.setSecurityManager(securityManager);    
       //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）    
       Subject subject = SecurityUtils.getSubject();   
       UsernamePasswordToken token = new UsernamePasswordToken(username, password);   
       try {    
           //4、登录，即身份验证    
           subject.login(token);  
  //5、角色，即角色查询  
           if(subject.hasRole("admin")) {    
               //有权限    
               System.out.println("当前用户拥有admin权限");  
           } else {    
               //无权限    
               System.out.println("当前用户没有admin权限");  
           }
       } catch (AuthenticationException e) {   
    	   e.printStackTrace();
           //5、身份验证失败    
           System.out.println("身份验证失败");  
       }finally{  
           return subject;
       }       
       
   }   
	public static Subject getsubject() {  
        return SecurityUtils.getSubject();  
    } 
}
