package excursions.daos.Jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import excursions.daos.PortageDao;
import excursions.daos.ScheduleConflictDao;
import excursions.models.Portage;

public class JdbcScheduleConflictDao implements ScheduleConflictDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbc;
    
    @Autowired
    private PortageDao portageDao;

	@Override
	public void detect(/*company id ?*/) {
		List<Portage> portages = portageDao.getPortages();
		
		//should we build endpoints to get the rest of what we need or
		//manually get the information here?
		
		
		
		
		//set the affected statuses here.

	}

}
