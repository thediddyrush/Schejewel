package excursions.daos.Jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import excursions.daos.TourGroupDao;
import excursions.models.TourGroup;

public class JdbcTourGroupDao implements TourGroupDao{
    @Autowired
    private NamedParameterJdbcTemplate jdbc;
    
	@Override
	public TourGroup createTourGroup(TourGroup tourGroup) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", tourGroup.getId());
		params.addValue("portage_id", tourGroup.getPortageId());
		params.addValue("tour_id", tourGroup.getTourId());
		params.addValue("group_size", tourGroup.getGroupSize());
		params.addValue("settled", tourGroup.isSettled());//TODO: does sql like boolean?
		String sql = "INSERT INTO tour_group(portage_id, tour_id, group_size, settled)"
				+ " VALUES(:portage_id, :tour_id, :group_size, :settled)";
		KeyHolder kh = new GeneratedKeyHolder();
		jdbc.update(sql, params, kh);
		tourGroup.setId(kh.getKey().intValue());
		return tourGroup;
	}

}
