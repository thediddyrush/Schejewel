package excursions.daos;

import excursions.models.Resource;
import java.util.List;

public interface ResourceDao {
    List<Resource> getResources(int companyId);
    List<Resource> getResourcesOnDate(int companyId, String date);
}
