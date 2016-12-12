
package legacy.daos.interfaces;

import legacy.models.TourType;
import javax.sql.DataSource;


public interface TourTypeDao {
	public void setDataSource(DataSource ds);
	public TourType getTourType(int tourTypeid);
	public TourType updateTourType(TourType tourType);
	public void deleteTourType(int tourTypeid);
	public TourType createTourType(TourType tourType);
}
