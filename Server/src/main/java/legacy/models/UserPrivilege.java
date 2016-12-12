
package legacy.models;

public class UserPrivilege {
	int userId;
	int privilegeId;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int id) {
		userId = id;
	}
	
	public int getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(int id) {
		privilegeId = id;
	}
}
