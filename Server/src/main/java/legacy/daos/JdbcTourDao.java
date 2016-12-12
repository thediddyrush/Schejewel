package legacy.daos;

import legacy.daos.interfaces.TourDao;
import legacy.models.Resource;
import legacy.models.Tour;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import javax.sql.DataSource;

import legacy.models.TourGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTourDao implements TourDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbc;
	
	@Override
	public void setDataSource(DataSource ds) {
		jdbc = new NamedParameterJdbcTemplate(ds);
	}

	@Override
	public List<Tour> getTours(int companyid) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("companyId",companyid);
		String sql = "SELECT id, owner_id AS ownerId, start_date AS startDate, start_time AS startTime, tour_type_id AS tourTypeId, status AS statusId FROM tour WHERE owner_id = :companyId";
        List<Tour> tours = jdbc.query(sql, params, new BeanPropertyRowMapper<>(Tour.class));

        sql = "SELECT id, name, start_time AS startTime, capacity, owner_id AS ownerId, tour_id AS tourId, duration, status AS statusId " +
                "FROM resource r, resource_schedule rs WHERE r.id = rs.resource_id AND r.owner_id = :companyId";
        List<Resource> resources = jdbc.query(sql, params, new BeanPropertyRowMapper<>(Resource.class));

        sql = "SELECT id, portage_id AS portageId, tour_id AS tourId, group_size AS groupSize, settled FROM tour_group WHERE id IN(SELECT id FROM tour WHERE owner_id = :companyId)";
        List<TourGroup> tourGroups = jdbc.query(sql, params, new BeanPropertyRowMapper<>(TourGroup.class));

        for(Tour tour : tours){
            //Assign resources to tours
            for(Resource resource : resources){
                if(resource.getTourId() == tour.getId()){
                    tour.addResource(resource);
                }
            }

            //Assign tour groups to tours
            for(TourGroup tourGroup : tourGroups){
                if(tourGroup.getTourId() == tour.getId()){
                    tour.addTourGroup(tourGroup);
                }
            }
        }

		return tours;
	}
	
	@Override
	public List<Tour> getToursByDateRange(int companyid, long beginDate, long endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("owner_id",companyid);
		calendar.setTimeInMillis(beginDate);
		params.addValue("begin",sdf.format(calendar.getTime()));
		calendar.setTimeInMillis(endDate);
		params.addValue("end", sdf.format(calendar.getTime()));
		String sql = "SELECT * FROM tour WHERE owner_id = :owner_id AND start_date BETWEEN "
			+ ":begin AND :end";
		List<Tour> tours = jdbc.query(sql, params, new TourRowMapper());
		return tours;
	}

	@Override
	public Tour getTour(int tourid) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",tourid);
        String sql = "SELECT * FROM tour WHERE id = :id";
        Tour tour = (Tour)jdbc.queryForObject(sql, params, new TourRowMapper());
		tour.setId(tourid);
        return tour;
	}

	@Override
	public Tour updateTour(Tour tour) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", tour.getId());
        params.addValue("owner_id",tour.getOwnerId());
		params.addValue("start_date", tour.getStartDate());
		params.addValue("start_time", tour.getStartTime());
		params.addValue("tour_type_id", tour.getTourTypeId());
		params.addValue("status", tour.getStatusId());
		String sql = "UPDATE tour SET owner_id=:owner_id, start_date=:start_date, "
			+ "start_time=:start_time, tour_type_id=:tour_type_id, status=:status WHERE id=:id";
		jdbc.update(sql, params);
		return tour;
	}

	@Override
	public void deleteTour(int tourid) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",tourid);
		String sql = "DELETE FROM tour WHERE id=:id";
		jdbc.update(sql, params);
	}

	@Override
	public Tour createTour(Tour tour) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", tour.getId());
        params.addValue("owner_id",tour.getOwnerId());
		params.addValue("start_date", tour.getStartDate());
		params.addValue("start_time", tour.getStartTime());
		params.addValue("tour_type_id", tour.getTourTypeId());
		params.addValue("status", tour.getStatusId());
		String sql = "INSERT INTO tour(owner_id, start_date, start_time, tour_type_id, status)"
			+ " VALUES(:owner_id, :start_date, :start_time, :tour_type_id, :status)";
		KeyHolder kh = new GeneratedKeyHolder();
		jdbc.update(sql, params, kh);
		tour.setId(kh.getKey().intValue());
		return tour;
	}
	
	public class TourRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			Tour tour = new Tour();
			tour.setId(rs.getInt("id"));
			tour.setOwnerId(rs.getInt("owner_id"));
			tour.setStartTime(rs.getString("start_date") + " " + rs.getString("start_time"));
			tour.setTourTypeId(rs.getInt("tour_type_id"));
			tour.setStatusId(rs.getInt("status"));
			return tour;
		}
	}
}
