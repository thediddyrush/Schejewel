package excursions.daos.Jdbc;

import excursions.daos.ResourceDao;
import excursions.models.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcResourceDao implements ResourceDao{
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public List<Resource> getResources(int companyId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("companyId", companyId);
        String sql = "SELECT id, name, start_time AS startTime, start_date AS startDate, capacity, owner_id AS ownerId, tour_id AS tourId, duration, status AS statusId " +
                "FROM resource r, resource_schedule rs WHERE r.id = rs.resource_id AND r.owner_id = :companyId";
        List<Resource> resources = jdbc.query(sql, params, new BeanPropertyRowMapper<>(Resource.class));
        return resources;
    }

    @Override
    public List<Resource> getResourcesOnDate(int companyId, String date) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("companyId", companyId);
        params.addValue("date", date);
        String sql = "SELECT id, name, start_time AS startTime, start_date AS startDate, capacity, owner_id AS ownerId, tour_id AS tourId, duration, status AS statusId " +
                "FROM resource r, resource_schedule rs WHERE r.id = rs.resource_id AND r.owner_id = :companyId AND start_date = STR_TO_DATE(:date,'%Y-%m-%d')";
        List<Resource> resources = jdbc.query(sql, params, new BeanPropertyRowMapper<>(Resource.class));
        return resources;
    }
}
