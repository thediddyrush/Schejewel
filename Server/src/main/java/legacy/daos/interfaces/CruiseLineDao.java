package legacy.daos.interfaces;

import legacy.models.CruiseLine;
import javax.sql.DataSource;
import java.util.List;

public interface CruiseLineDao {
	public void setDataSource(DataSource ds);
	public CruiseLine getCruiseLine(int cruiseLineId);
	public CruiseLine updateCruiseLine(CruiseLine cruiseLine);
	public void deleteCruiseLine(int cruiseLineId);
	public CruiseLine createCruiseLine(CruiseLine cruiseLine);
    public List<CruiseLine> getCruiseLines();
}
