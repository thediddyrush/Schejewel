
package legacy.daos;

import legacy.daos.interfaces.TourTypeDao;
import legacy.models.TourType;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


public class JdbcTourTypeDao implements TourTypeDao {
	@Autowired
    private NamedParameterJdbcTemplate jdbc;
	
	@Override
	public void setDataSource(DataSource ds) {
		jdbc = new NamedParameterJdbcTemplate(ds);
	}

	@Override
	public TourType getTourType(int tourTypeid) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",tourTypeid);
        String sql = "SELECT * FROM tour_type WHERE id = :id";
        TourType tourType = jdbc.queryForObject(sql, params, new BeanPropertyRowMapper<>(TourType.class));
		tourType.setTourTypeId(tourTypeid);
        return tourType;
	}

	@Override
	public TourType updateTourType(TourType tourType) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id",tourType.getTourTypeId());
        params.addValue("name",tourType.getName());
		params.addValue("owner_id", tourType.getCompanyId());
		String sql = "UPDATE tour_type SET name=:name, owner_id=:owner_id WHERE id=:id";
		jdbc.update(sql, params);
		return tourType;
	}

	@Override
	public void deleteTourType(int tourTypeid) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",tourTypeid);
		String sql = "DELETE FROM tour_type WHERE id=:id";
		jdbc.update(sql, params);
	}

	@Override
	public TourType createTourType(TourType tourType) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", tourType.getName());
		params.addValue("owner_id", tourType.getCompanyId());
		String sql = "INSERT INTO tour_type(name, owner_id)"
			+ " VALUES(:name,:owner_id)";
		KeyHolder kh = new GeneratedKeyHolder();
		jdbc.update(sql, params, kh);
		tourType.setTourTypeId(kh.getKey().intValue());
		return tourType;
	}
	
}
