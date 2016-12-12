package legacy.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class ResourceController {
    /*
    @Autowired
    private ResourceDao resourceDao = new JdbcResourceDao();

    @RequestMapping(value = "/api/resource", method = RequestMethod.GET)
    public @ResponseBody
    List<Resource> getResources(@RequestHeader(value="X-AUTH-TOKEN") String token){
        return resourceDao.getResources(Converter.fromJSON(Converter.fromBase64(token)).getCompanyId());
    }

    @RequestMapping(value = "/api/resource/{starttime}", method = RequestMethod.GET)
    public @ResponseBody List<Resource> getResources(@RequestHeader(value="X-AUTH-TOKEN") String token, @PathVariable(value = "starttime") String startTime){
        return resourceDao.getResources(Converter.fromJSON(Converter.fromBase64(token)).getCompanyId(), startTime);
    }

    @RequestMapping(value = "/api/resource/{resourceId}", method = RequestMethod.GET)
    public @ResponseBody Resource getResource(@PathVariable(value = "resourceId") int ResourceId){
        return resourceDao.getResource(ResourceId);
    }

    @RequestMapping(value = "/api/resource", method = RequestMethod.PUT)
    public @ResponseBody Resource updateResource(@RequestBody Resource toUpdate){
        return resourceDao.updateResource(toUpdate);
    }

    @RequestMapping(value = "/api/resource/{resourceId}", method = RequestMethod.DELETE)
    public @ResponseBody Resource deleteResource(@PathVariable(value = "resourceId") int ResourceId){
        return resourceDao.deleteResource(ResourceId);//this is null as of now.  don't try to access what this returns
    }

    @RequestMapping(value = "/api/resource", method = RequestMethod.POST)
    public @ResponseBody Resource createResource(@RequestBody Resource toCreate){
        return resourceDao.createResource(toCreate);
    }*/
}