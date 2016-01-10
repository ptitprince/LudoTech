package frontend.login.view;

public interface LoginObserver {

	public void notifySuccessfulLogin(int memberID, boolean isAdmin);
	
}
