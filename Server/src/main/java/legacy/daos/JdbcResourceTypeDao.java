
package legacy.daos;

import legacy.daos.interfaces.ResourceTypeDao;
import legacy.models.ResourceType;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcResourceTypeDao implements ResourceTypeDao {
@Autowired
    private NamedParameterJdbcTemplate jdbc;
	
	@Override
	public void setDataSource(DataSource ds) {
		jdbc = new NamedParameterJdbcTemplate(ds);
	}

	@Override
	public ResourceType getResourceType(int resourceTypeId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",resourceTypeId);
        String sql = "SELECT * FROM resource_type WHERE id = :id";
        ResourceType resourceType = jdbc.queryForObject(sql, params, new BeanPropertyRowMapper<>(ResourceType.class));
		resourceType.setResourceTypeId(resourceTypeId);
        return resourceType;
	}

	@Override
	public ResourceType updateResourceType(ResourceType resourceType) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id",resourceType.getResourceTypeId());
        params.addValue("name",resourceType.getName());
		String sql = "UPDATE resource_type SET name=:name WHERE id=:id";
		jdbc.update(sql, params);
		return resourceType;
	}

	@Override
	public void deleteResourceType(int resourceTypeId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",resourceTypeId);
		String sql = "DELETE FROM resource_type WHERE id=:id";
		jdbc.update(sql, params);
	}

	@Override
	public ResourceType createResourceType(ResourceType resourceType) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", resourceType.getName());
		String sql = "INSERT INTO resource_type(name)"
			+ " VALUES(:name)";
		KeyHolder kh = new GeneratedKeyHolder();
		jdbc.update(sql, params, kh);
		resourceType.setResourceTypeId(kh.getKey().intValue());
		return resourceType;
	}
	
}
