
package legacy.daos;

import legacy.daos.interfaces.ResourceResourceTypeDao;
import legacy.models.ResourceResourceType;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class JdbcResourceResourceTypeDao implements ResourceResourceTypeDao{
	@Autowired
    private NamedParameterJdbcTemplate jdbc;
	
	@Override
	public void setDataSource(DataSource ds) {
		jdbc = new NamedParameterJdbcTemplate(ds);
	}

	@Override
	public ResourceResourceType createResourceResourceType(ResourceResourceType resourceResourceType) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", resourceResourceType.geResourceId());
		params.addValue("resource_type_id", resourceResourceType.getResourceTypeId());
		String sql = "INSERT INTO resource_resource_type (resource_id, resource_type_id)"
			+ " VALUES(:id, :resource_type_id)";
		jdbc.update(sql, params);
		return resourceResourceType;
	}
	
	@Override
	public void deleteResourceResourceType(ResourceResourceType resourceResourceType) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", resourceResourceType.geResourceId());
		params.addValue("resource_type_id", resourceResourceType.getResourceTypeId());
		String sql = "DELETE FROM resource_resource_type WHERE resource_id=:id AND resource_type_id=:resource_type_id";
		jdbc.update(sql, params);
	}

	@Override
	public List<Integer> getResourceTypes(int resourceId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",resourceId);
        String sql = "SELECT resource_resource_type.resource_type_id FROM resource_resource_type "
			+ "WHERE resource_id = :id";
        List<Integer> privileges = jdbc.queryForList(sql, params, Integer.class);
        return privileges;
	}
}
