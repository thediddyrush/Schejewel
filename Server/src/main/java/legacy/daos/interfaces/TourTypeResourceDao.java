
package legacy.daos.interfaces;

import legacy.models.TourTypeResource;
import java.util.List;
import javax.sql.DataSource;

public interface TourTypeResourceDao {
	public void setDataSource(DataSource ds);
	public TourTypeResource createTourTypeResource(TourTypeResource tourTypeResource);
	public List<TourTypeResource> getTourTypeResourcesByTourTypeId(int tourTypeId);
	public List<TourTypeResource> getTourTypeResourcesByResourceTypeId(int resourceTypeId);
	public void deleteTourTypeResource(TourTypeResource tourTypeResource);
}
