package legacy.daos;

import legacy.daos.interfaces.StatusDao;
import legacy.models.Status;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


public class JdbcStatusDao implements StatusDao {
	@Autowired
    private NamedParameterJdbcTemplate jdbc;
	
	@Override
	public void setDataSource(DataSource ds) {
		jdbc = new NamedParameterJdbcTemplate(ds);
	}

	@Override
	public Status getStatus(int statusid) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",statusid);
        String sql = "SELECT * FROM status WHERE id = :id";
        Status status = jdbc.queryForObject(sql, params, new BeanPropertyRowMapper<>(Status.class));
		status.setStatusId(statusid);
        return status;
	}

	@Override
	public Status updateStatus(Status status) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id",status.getStatusId());
        params.addValue("description",status.getDescription());
		String sql = "UPDATE status SET description=:description WHERE id=:id";
		jdbc.update(sql, params);
		return status;
	}

	@Override
	public void deleteStatus(int statusid) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",statusid);
		String sql = "DELETE FROM status WHERE id=:id";
		jdbc.update(sql, params);
	}

	@Override
	public Status createStatus(Status status) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("description", status.getDescription());
		String sql = "INSERT INTO status(description)"
			+ " VALUES(:description)";
		KeyHolder kh = new GeneratedKeyHolder();
		jdbc.update(sql, params, kh);
		status.setStatusId(kh.getKey().intValue());
		return status;
	}
	
}
