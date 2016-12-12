package legacy.daos.interfaces;

import java.util.List;
import javax.sql.DataSource;
import legacy.models.Resource;

public interface ResourceDao {
	public void setDataSource(DataSource ds);
	public List<Resource> getResources(int companyId);
	public Resource getResource(int resourceId);
	public List<Resource> getResources(int companyId, String startDate);
	public Resource updateResource(Resource toUpdate);
	public Resource deleteResource(int resourceId);
	public Resource createResource(Resource toCreate);
}
