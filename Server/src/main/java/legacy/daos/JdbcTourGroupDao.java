package legacy.daos;

import legacy.daos.interfaces.TourGroupDao;
import legacy.models.TourGroup;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTourGroupDao implements TourGroupDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbc;
	
	@Override
	public void setDataSource(DataSource ds) {
		jdbc = new NamedParameterJdbcTemplate(ds);
	}

	@Override
	public TourGroup createTourGroup(TourGroup tourGroup) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("portage_id",tourGroup.getPortageId());
		params.addValue("tour_id", tourGroup.getTourId());
		params.addValue("group_size", tourGroup.getGroupSize());
		params.addValue("settled", tourGroup.isSettled());
		String sql = "INSERT INTO tour_group(portage_id, tour_id, group_size, settled)"
			+ " VALUES(:portage_id, :tour_id, :group_size, :settled)";
		KeyHolder kh = new GeneratedKeyHolder();
		jdbc.update(sql, params, kh);
		tourGroup.setId(kh.getKey().intValue());
		return tourGroup;
	}

	@Override
	public TourGroup getTourGroup(int tourGroupId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",tourGroupId);
        String sql = "SELECT * FROM tour_group WHERE id = :id";
        TourGroup tourGroup = (TourGroup)jdbc.queryForObject(sql, params, new TourGroupRowMapper());
        return tourGroup;
	}

	@Override
	public List<TourGroup> getTourGroups(int companyId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("owner_id",companyId);
		String sql = "SELECT tour_group.*, tour.owner_id FROM tour_group "
			+ "JOIN tour ON tour_group.tour_id = tour.id WHERE owner_id = :owner_id";
		List<TourGroup> tourGroups = jdbc.query(sql, params, new TourGroupRowMapper());
		return tourGroups;
	}

	@Override
	public List<TourGroup> getTourGroupsByTourId(int companyId, int tourId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("owner_id",companyId);
		params.addValue("tour_id", tourId);
		String sql = "SELECT tour_group.*, tour.owner_id FROM tour_group "
			+ "JOIN tour ON tour_group.tour_id = tour.id WHERE owner_id = :owner_id AND"
			+ " tour_id = :tour_id";
		List<TourGroup> tourGroups = jdbc.query(sql, params, new TourGroupRowMapper());
		return tourGroups;
	}

	@Override
	public List<TourGroup> getTourGroupsBySettledStatus(int companyId, boolean settled) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("owner_id",companyId);
		params.addValue("settled", settled);
		String sql = "SELECT tour_group.*, tour.owner_id FROM tour_group "
			+ "JOIN tour ON tour_group.tour_id = tour.id WHERE owner_id = :owner_id AND"
			+ " settled = :settled";
		List<TourGroup> tourGroups = jdbc.query(sql, params, new TourGroupRowMapper());
		return tourGroups;
	}

	@Override
	public TourGroup updateTourGroup(TourGroup tourGroup) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", tourGroup.getId());
        params.addValue("portage_id",tourGroup.getPortageId());
		params.addValue("tour_id", tourGroup.getTourId());
		params.addValue("group_size", tourGroup.getGroupSize());
		params.addValue("settled", tourGroup.isSettled());
		String sql = "UPDATE tour_group SET portage_id=:portage_id, tour_id=:tour_id, "
			+ "group_size=:group_size, settled=:settled WHERE id=:id";
		jdbc.update(sql, params);
		return tourGroup;
	}

	@Override
	public void deleteTourGroup(int tourGroupId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",tourGroupId);
		String sql = "DELETE FROM tour_group WHERE id=:id";
		jdbc.update(sql, params);
	}
	
    public class TourGroupRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			TourGroup tourGroup = new TourGroup();
			tourGroup.setId(rs.getInt("id"));
			tourGroup.setPortageId(rs.getInt("portage_id"));
			tourGroup.setTourId(rs.getInt("tour_id"));
			tourGroup.setGroupSize(rs.getInt("group_size"));
			tourGroup.setSettled(rs.getBoolean("settled"));
			return tourGroup;
		}
	}
}
