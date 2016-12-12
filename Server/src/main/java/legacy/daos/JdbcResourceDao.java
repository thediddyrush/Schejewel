package legacy.daos;

import legacy.daos.interfaces.ResourceDao;
import legacy.models.Resource;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Repository
public class JdbcResourceDao implements ResourceDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

	@Override
    public void setDataSource(DataSource ds) {
        jdbc = new NamedParameterJdbcTemplate(ds);
    }

    @Override
    public List<Resource> getResources(int companyId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("companyId", companyId);
        String sql = "SELECT * FROM resource WHERE owner_id = :companyId";
        List<Resource> resources = jdbc.query(sql, params, new ResourceRowMapper());
        return resources;
    }

    @Override
    public Resource getResource(int resourceId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", resourceId);
        String sql = "SELECT * FROM resource WHERE id = :id";
        Resource resource = (Resource)jdbc.queryForObject(sql, params, new ResourceRowMapper());
        return resource;
    }

    @Override
    public Resource updateResource(Resource toUpdate) {
        MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", toUpdate.getId());
		params.addValue("name", toUpdate.getName());
		params.addValue("capacity", toUpdate.getCapacity());
        params.addValue("owner_id", toUpdate.getOwnerId());
        String sql = "UPDATE resource SET name = :name, capacity = :capacity, owner_id = :owner_id"
			+ " WHERE id = :id";
        jdbc.update(sql, params);
        return toUpdate;
    }

    @Override
    public Resource deleteResource(int resourceId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", resourceId);
        String sql = "DELETE FROM resource WHERE id = :id";
        jdbc.update(sql, params);
        return null;
    }

    @Override
    public Resource createResource(Resource toCreate) {
        MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", toCreate.getName());
		params.addValue("capacity", toCreate.getCapacity());
        params.addValue("owner_id", toCreate.getOwnerId());
        String sql = "INSERT INTO resource(name,capacity,owner_id) VALUES(:name,:capacity,:owner_id)";
		
		KeyHolder kh = new GeneratedKeyHolder();
		jdbc.update(sql, params, kh);
		toCreate.setId(kh.getKey().intValue());
		return toCreate;
    }

	@Override
	public List<Resource> getResources(int companyId, String startDate) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("owner_id", companyId);
		params.addValue("begin", startDate);
        String sql = "SELECT id, name, start_time AS startTime, capacity, owner_id AS ownerId, tour_id AS tourId, duration, status AS statusId FROM resource JOIN resource_schedule ON resource.id = "
			+ "resource_schedule.resource_id WHERE resource.owner_id = :owner_id AND "
			+ "resource_schedule.start_date =:begin";
        List<Resource> resources = jdbc.query(sql, params, new BeanPropertyRowMapper<>(Resource.class));
        return resources;
	}
	
	public class ResourceRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Resource resource = new Resource();
			resource.setId(rs.getInt("id"));
			resource.setName(rs.getString("name"));
			//resource.setStartTime(rs.getString("StartTime"));
			resource.setCapacity(rs.getInt("capacity"));
			resource.setOwnerId(rs.getInt("owner_id"));
			return resource;
		}
	}
}
