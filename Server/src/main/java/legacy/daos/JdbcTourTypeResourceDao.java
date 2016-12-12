
package legacy.daos;

import legacy.daos.interfaces.TourTypeResourceDao;
import legacy.models.TourTypeResource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class JdbcTourTypeResourceDao implements TourTypeResourceDao{
	@Autowired
    private NamedParameterJdbcTemplate jdbc;
	
	@Override
	public void setDataSource(DataSource ds) {
		jdbc = new NamedParameterJdbcTemplate(ds);
	}
	
	@Override
	public TourTypeResource createTourTypeResource(TourTypeResource tourTypeResource) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("tour_type_id", tourTypeResource.getTourTypeId());
		params.addValue("resource_type_id", tourTypeResource.getResourceTypeId());
		params.addValue("order_number", tourTypeResource.getOrderNumber());
		params.addValue("duration", tourTypeResource.getDuration());
		String sql = "INSERT INTO tour_type_resource (tour_type_id, resource_type_id, order_number, duration)"
			+ " VALUES(:tour_type_id, :resource_type_id, :order_number, :duration)";
		jdbc.update(sql, params);
		return tourTypeResource;
	}

	@Override
	public List<TourTypeResource> getTourTypeResourcesByTourTypeId(int tourTypeId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("tour_type_id", tourTypeId);
        String sql = "SELECT * FROM tour_type_resource WHERE tour_type_id = :tour_type_id";
        List<TourTypeResource> tourTypeResources = jdbc.query(sql, params, new TourTypeResourceRowMapper());
        return tourTypeResources;
	}

	@Override
	public List<TourTypeResource> getTourTypeResourcesByResourceTypeId(int resourceTypeId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("resource_type_id", resourceTypeId);
        String sql = "SELECT * FROM tour_type_resource WHERE resource_type_id = :resource_type_id";
        List<TourTypeResource> tourTypeResources = jdbc.query(sql, params, new TourTypeResourceRowMapper());
        return tourTypeResources;
	}

	@Override
	public void deleteTourTypeResource(TourTypeResource tourTypeResource) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("tour_type_id", tourTypeResource.getTourTypeId());
		params.addValue("resource_type_id", tourTypeResource.getResourceTypeId());
		params.addValue("order_number", tourTypeResource.getOrderNumber());
		params.addValue("duration", tourTypeResource.getDuration());
		String sql = "DELETE FROM tour_type_resource WHERE tour_type_id=:tour_type_id AND "
			+ "resource_type_id=:resource_type_id AND order_number=:order_number AND duration=:duration";
		jdbc.update(sql, params);
	}
	
	public class TourTypeResourceRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			TourTypeResource ttr = new TourTypeResource();
			ttr.setTourTypeId(rs.getInt("tour_type_id"));
			ttr.setResourceTypeId(rs.getInt("resource_type_id"));
			ttr.setOrderNumber(rs.getInt("order_number"));
			ttr.setDuration(rs.getInt("duration"));
			return ttr;
		}
	}
	
}
