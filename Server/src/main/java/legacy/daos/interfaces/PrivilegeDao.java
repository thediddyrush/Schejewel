
package legacy.daos.interfaces;

import legacy.models.Privilege;
import javax.sql.DataSource;

public interface PrivilegeDao {
	public void setDataSource(DataSource ds);
	public Privilege getPrivilege(int privilegeId);
	public Privilege updatePrivilege(Privilege privilege);
	public void deletePrivilege(int privilegeId);
	public Privilege createPrivilege(Privilege privilege);
}
