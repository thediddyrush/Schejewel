
package legacy.daos.interfaces;

import legacy.models.ResourceType;
import javax.sql.DataSource;

public interface ResourceTypeDao {
	public void setDataSource(DataSource ds);
	public ResourceType getResourceType(int resourceTypeId);
	public ResourceType updateResourceType(ResourceType resourceType);
	public void deleteResourceType(int resourceTypeId);
	public ResourceType createResourceType(ResourceType resourceType);
}
