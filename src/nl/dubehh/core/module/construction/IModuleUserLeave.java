package nl.dubehh.core.module.construction;

import nl.dubehh.core.user.User;

public interface IModuleUserLeave {
	
	public void onUserLeave(User user, boolean disconnected);
}
