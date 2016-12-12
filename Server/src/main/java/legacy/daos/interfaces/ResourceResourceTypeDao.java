
package legacy.daos.interfaces;

import legacy.models.ResourceResourceType;
import java.util.List;
import javax.sql.DataSource;

public interface ResourceResourceTypeDao {
	public void setDataSource(DataSource ds);
	public List<Integer> getResourceTypes(int resourceId);
	public void deleteResourceResourceType(ResourceResourceType resourceResourceType);
	public ResourceResourceType createResourceResourceType(ResourceResourceType resourceResourceType);
}
