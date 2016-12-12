package legacy.daos.interfaces;

import legacy.models.User;
import java.util.List;
import javax.sql.DataSource;

public interface UserDao {
	public void setDataSource(DataSource ds);
    public User findUserByUsername(String username);
	public User getUser(int userid);
	public List<User> getUsers(int companyid);
    public User createUser(User user);
	public void deleteUser(int userid);
	public User updateUser(User user);
}
