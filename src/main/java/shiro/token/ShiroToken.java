package shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

public class ShiroToken extends UsernamePasswordToken  implements java.io.Serializable{

	public ShiroToken(String username, String pswd) {
		super(username,pswd);
		this.pswd = pswd ;
	}

	private String pswd ;

	public String getPswd() {
		return pswd;
	}


	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	
	
	
	
	
}
