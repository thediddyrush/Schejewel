package legacy.daos.interfaces;

import legacy.models.TourGroup;
import java.util.List;
import javax.sql.DataSource;

public interface TourGroupDao {
	public void setDataSource(DataSource ds);
	public TourGroup createTourGroup(TourGroup tourGroup);
	public TourGroup getTourGroup(int tourGroupId);
	public List<TourGroup> getTourGroups(int companyId);
	public List<TourGroup> getTourGroupsByTourId(int companyId, int tourId);
	public List<TourGroup> getTourGroupsBySettledStatus(int companyId, boolean settled);
	public TourGroup updateTourGroup(TourGroup tourGroup);
	public void deleteTourGroup(int tourGroupId);
}
