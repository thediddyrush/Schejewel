package legacy.daos.interfaces;

import legacy.models.Tour;
import java.util.List;
import javax.sql.DataSource;

public interface TourDao {
	public void setDataSource(DataSource ds);
	public List<Tour> getTours(int companyid);
	public List<Tour> getToursByDateRange(int companyid, long beginDate, long endDate);
	public Tour getTour(int tourid);
	public Tour updateTour(Tour tour);
	public void deleteTour(int tourid);
	public Tour createTour(Tour tour);
}
