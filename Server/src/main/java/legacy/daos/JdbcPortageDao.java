package legacy.daos;

import legacy.daos.interfaces.PortageDao;
import legacy.models.Portage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcPortageDao implements PortageDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbc;
	
	@Override
	public void setDataSource(DataSource ds) {
		jdbc = new NamedParameterJdbcTemplate(ds);
	}

	@Override
	public Portage createPortage(Portage portage) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("cruise_ship_id", portage.getCruiseShipId());
        params.addValue("arrival_date", portage.getArrivalDate());
		params.addValue("arrival_time", portage.getArrivalTime());
		params.addValue("departure_date", portage.getDepartureDate());
		params.addValue("departure_time", portage.getDepartureTime());
		params.addValue("passengers", portage.getPassengerCount());
		params.addValue("AA", portage.getAllAboardTime());
		params.addValue("dock", portage.getDock());
		params.addValue("voyage", portage.getVoyage());
		params.addValue("location", portage.getLocation());
		String sql = "INSERT INTO portage(cruise_ship_id, arrival_date, arrival_time,"
				+ " departure_date, departure_time, passengers, AA, dock, voyage, location)"
			+ " VALUES(:cruise_ship_id, :arrival_date, :arrival_time, :departure_date,"
				+ " :departure_time, :passengers, :AA, :dock, :voyage, :location)";
		KeyHolder kh = new GeneratedKeyHolder();
		jdbc.update(sql, params, kh);
		portage.setPortageId(kh.getKey().intValue());
		return portage;
	}

	@Override
	public Portage getPortage(int portageId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",portageId);
        String sql = "SELECT * FROM portage WHERE id = :id";
        Portage portage = (Portage)jdbc.queryForObject(sql, params, new PortageRowMapper());
        return portage;
	}

    @Override
	public List<Portage> getPortageByCruiseShipId(int cruiseShipId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("cruise_ship_id",cruiseShipId);
		String sql = "SELECT * FROM portage WHERE cruise_ship_id = :cruise_ship_id";
		List<Portage> portages = jdbc.query(sql, params, new PortageRowMapper());
		return portages;
	}

	@Override
	public List<Portage> getPortageByCruiseLineId(int cruiseLineId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("cruise_line_id",cruiseLineId);
		String sql = "SELECT * FROM portage JOIN cruise_ship ON portage.cruise_ship_id=cruise_ship.id "
			+ "WHERE cruise_line_id=:cruise_line_id";
		List<Portage> portages = jdbc.query(sql, params, new PortageRowMapper());
		return portages;
	}

	@Override
	public List<Portage> getPortageByArrivalRange(long beginDate, long endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		calendar.setTimeInMillis(beginDate);
		params.addValue("begin",sdf.format(calendar.getTime()));
		calendar.setTimeInMillis(endDate);
		params.addValue("end", sdf.format(calendar.getTime()));
		String sql = "SELECT * FROM portage WHERE arrival_date BETWEEN :begin AND :end";
		List<Portage> portages = jdbc.query(sql, params, new PortageRowMapper());
		return portages;
	}

	@Override
	public Portage updatePortage(Portage portage) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", portage.getPortageId());
		params.addValue("cruise_ship_id", portage.getCruiseShipId());
        params.addValue("arrival_date", portage.getArrivalDate());
		params.addValue("arrival_time", portage.getArrivalTime());
		params.addValue("departure_date", portage.getDepartureDate());
		params.addValue("departure_time", portage.getDepartureTime());
		params.addValue("passengers", portage.getPassengerCount());
		params.addValue("AA", portage.getAllAboardTime());
		params.addValue("dock", portage.getDock());
		params.addValue("voyage", portage.getVoyage());
		params.addValue("location", portage.getLocation());
		String sql = "UPDATE portage SET cruise_ship_id=:cruise_ship_id, arrival_date=:arrival_date, "
			+ "arrival_time=:arrival_time, departure_date=:departure_date, departure_time=:departure_time, "
			+ "passengers=:passengers, AA=:AA, dock=:dock, voyage=:voyage, location=:location WHERE id=:id";
		jdbc.update(sql, params);
		return portage;
	}

	@Override
	public void deletePortage(int portageId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",portageId);
		String sql = "DELETE FROM portage WHERE id=:id";
		jdbc.update(sql, params);
	}
	
	@Override
	public List<Portage> getPortages(int companyId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("owner_id",companyId);
		String sql = "SELECT portage.* FROM tour_group " +
			"JOIN tour ON tour_group.tour_id = tour.id " +
			"JOIN portage ON tour_group.portage_id = portage.id " +
			"WHERE owner_id = :owner_id;";
		List<Portage> portages = jdbc.query(sql, params, new PortageRowMapper());
		return portages;
	}
	
    public class PortageRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			Portage portage = new Portage();
			portage.setPortageId(rs.getInt("id"));
			portage.setCruiseShipId(rs.getInt("cruise_ship_id"));
			portage.setArrival(rs.getDate("arrival_date", cal), rs.getTime("arrival_time"));
			portage.setDeparture(rs.getDate("departure_date", cal), rs.getTime("departure_time"));
			
			//deal with columns that may be null
			int passengerCount = rs.getInt("passengers");
			if (rs.wasNull())
				portage.setPassengerCount(null);
			else
				portage.setPassengerCount(passengerCount);
			
			Time time = rs.getTime("AA");
			
			if (rs.wasNull())
				portage.setAllAboardTime(null);
			else
				portage.setAllAboardTime(time);
			
			int dock = rs.getInt("dock");
			if (rs.wasNull())
				portage.setDock(null);
			else
				portage.setDock(dock);
			
			String voyage = rs.getString("voyage");
			if (rs.wasNull())
				portage.setVoyage(null);
			else
				portage.setVoyage(voyage);
			
			String location = rs.getString("location");
			if (rs.wasNull())
				portage.setLocation(null);
			else
				portage.setLocation(location);
			
			return portage;
		}
	}
}
