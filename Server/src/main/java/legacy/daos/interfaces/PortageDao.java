package legacy.daos.interfaces;

import legacy.models.Portage;
import java.util.List;
import javax.sql.DataSource;

public interface PortageDao {
	public void setDataSource(DataSource ds);
	public Portage createPortage(Portage portage);
	public Portage getPortage(int portageId);
    public List<Portage> getPortages(int companyId);
	public List<Portage> getPortageByCruiseShipId(int cruiseShipId);
	public List<Portage> getPortageByCruiseLineId(int cruiseLineId);
	public List<Portage> getPortageByArrivalRange(long beginDate, long endDate);
	public Portage updatePortage(Portage portage);
	public void deletePortage(int portageId);
}
