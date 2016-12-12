package excursions.daos.Jdbc;

import excursions.daos.PortageDao;
import excursions.models.Portage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcPortageDao implements PortageDao{
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public List<Portage> getPortages() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = "SELECT p.id, s.name, p.cruise_ship_id AS cruiseShipId, p.arrival_date AS arrivalDate, p.arrival_time AS arrivalTime, p.departure_date AS departureDate, " +
                "p.departure_time AS departureTime, p.passengers AS passengerCount, p.aa AS allAboard, p.dock, p.voyage, p.location FROM portage p, cruise_ship s WHERE " +
                "s.id = p.cruise_ship_id";
        List<Portage> portages = jdbc.query(sql, params, new BeanPropertyRowMapper<>(Portage.class));
        return portages;
    }
    
	@Override
	public Portage updatePortage(Portage portage) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", portage.getId());
		params.addValue("cruise_ship_id", portage.getCruiseShipId());
        params.addValue("arrival_date", portage.getArrivalDate());
		params.addValue("arrival_time", portage.getArrivalTime());
		params.addValue("departure_date", portage.getDepartureDate());
		params.addValue("departure_time", portage.getDepartureTime());
		params.addValue("passengers", portage.getPassengerCount());
		params.addValue("AA", portage.getAllAboard());
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
		return;
	}
}
