
package client.service.login;

import vo.PlayerVO;

public interface LoginResultHandler {
	
	enum Focus { ID, PASSWORD }
		
	public void loginSuccess(PlayerVO player);
	public void loginFailure(Focus focus,String reason);
	
} //interface LoginResultHandler;
