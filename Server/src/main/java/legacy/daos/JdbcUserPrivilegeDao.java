
package legacy.daos;

import legacy.daos.interfaces.UserPrivilegeDao;
import legacy.models.UserPrivilege;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


public class JdbcUserPrivilegeDao implements UserPrivilegeDao {
	@Autowired
    private NamedParameterJdbcTemplate jdbc;
	
	@Override
	public void setDataSource(DataSource ds) {
		jdbc = new NamedParameterJdbcTemplate(ds);
	}

	@Override
	public UserPrivilege createUserPrivilege(UserPrivilege userPrivilege) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userPrivilege.getUserId());
		params.addValue("privilege_id", userPrivilege.getPrivilegeId());
		String sql = "INSERT INTO user_privileges (user_id, privilege_id)"
			+ " VALUES(:user_id, :privilege_id)";
		jdbc.update(sql, params);
		return userPrivilege;
	}
	
	@Override
	public void deleteUserPrivilege(UserPrivilege userPrivilege) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userPrivilege.getUserId());
		params.addValue("privilege_id", userPrivilege.getPrivilegeId());
		String sql = "DELETE FROM user_privileges WHERE user_id=:user_id AND privilege_id=:privilege_id";
		jdbc.update(sql, params);
	}

	@Override
	public List<Integer> getUserPrivileges(int userId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id",userId);
        String sql = "SELECT user_privileges.privilege_id FROM user_privileges WHERE user_id = :user_id";
        List<Integer> privileges = jdbc.queryForList(sql, params, Integer.class);
        return privileges;
	}

	
	
}
