package excursions.controllers;

import excursions.daos.ResourceDao;
import excursions.models.Resource;
import excursions.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ResourceController {
    @Autowired
    private ResourceDao resourceDao;

    @RequestMapping(value = "/api/resources", method = RequestMethod.GET)
    public @ResponseBody List<Resource> getResources(@RequestHeader(value="X-AUTH-TOKEN") String token){
        return resourceDao.getResources(Converter.fromJSON(Converter.fromBase64(token)).getCompanyId());
    }

    @RequestMapping(value = "/api/resources/{date}", method = RequestMethod.GET)
    public @ResponseBody List<Resource> getResourcesOnDate(@RequestHeader(value="X-AUTH-TOKEN") String token, @PathVariable(value = "date") String date){
        return resourceDao.getResourcesOnDate(Converter.fromJSON(Converter.fromBase64(token)).getCompanyId(), date);
    }
}
