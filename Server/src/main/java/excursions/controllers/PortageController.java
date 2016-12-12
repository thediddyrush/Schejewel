package excursions.controllers;

import excursions.daos.PortageDao;
import excursions.daos.ScheduleConflictDao;
import excursions.models.Portage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PortageController {
    @Autowired
    private PortageDao portageDao;
    
    @Autowired
    private ScheduleConflictDao scheduleConflictDao;

    @RequestMapping(value = "/api/portages", method = RequestMethod.GET)
    public @ResponseBody List<Portage> getPortages(@RequestHeader(value="X-AUTH-TOKEN") String token){
        return portageDao.getPortages();
    }
    
    @RequestMapping(value = "/api/portage", method = RequestMethod.PUT)
    public @ResponseBody Portage updatePortage(@RequestBody Portage portage){
    	Portage temp = portageDao.updatePortage(portage);
    	scheduleConflictDao.detect();
    	return temp;
    }

    //TODO: Sql out of date; 4/3/2015 not sure what this is here for
    @RequestMapping(value = "/api/portage/{portageid}", method = RequestMethod.DELETE)
    public @ResponseBody void deletePortage(@PathVariable(value="portageid") int portageId){
        portageDao.deletePortage(portageId);
        scheduleConflictDao.detect();
    }
}
