
package legacy.daos.interfaces;

import legacy.models.UserPrivilege;
import java.util.List;
import javax.sql.DataSource;

public interface UserPrivilegeDao {
	public void setDataSource(DataSource ds);
	public List<Integer> getUserPrivileges(int userId);
	public void deleteUserPrivilege(UserPrivilege userPrivilege);
	public UserPrivilege createUserPrivilege(UserPrivilege userPrivilege);
}
