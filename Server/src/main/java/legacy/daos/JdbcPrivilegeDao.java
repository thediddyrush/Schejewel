
package legacy.daos;

import legacy.daos.interfaces.PrivilegeDao;
import legacy.models.Privilege;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcPrivilegeDao implements PrivilegeDao {
	@Autowired
    private NamedParameterJdbcTemplate jdbc;
	
	@Override
	public void setDataSource(DataSource ds) {
		jdbc = new NamedParameterJdbcTemplate(ds);
	}

	@Override
	public Privilege getPrivilege(int privilegeId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",privilegeId);
        String sql = "SELECT * FROM privilege WHERE id = :id";
        Privilege privilege = jdbc.queryForObject(sql, params, new BeanPropertyRowMapper<>(Privilege.class));
		privilege.setPrivilegeId(privilegeId);
        return privilege;
	}

	@Override
	public Privilege updatePrivilege(Privilege privilege) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id",privilege.getPrivilegeId());
        params.addValue("description",privilege.getDescription());
		String sql = "UPDATE privilege SET description=:description WHERE id=:id";
		jdbc.update(sql, params);
		return privilege;
	}

	@Override
	public void deletePrivilege(int privilegeId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",privilegeId);
		String sql = "DELETE FROM privilege WHERE id=:id";
		jdbc.update(sql, params);
	}

	@Override
	public Privilege createPrivilege(Privilege privilege) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("description", privilege.getDescription());
		String sql = "INSERT INTO privilege (description)"
			+ " VALUES(:description)";
		
		KeyHolder kh = new GeneratedKeyHolder();
		jdbc.update(sql, params, kh);
		privilege.setPrivilegeId(kh.getKey().intValue());
		return privilege;
	}
	
}
