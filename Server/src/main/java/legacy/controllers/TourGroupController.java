package legacy.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class TourGroupController {
    /*
    @Autowired
    private TourGroupDao tourGroupDao = new JdbcTourGroupDao();

    @RequestMapping(value = "/api/tourgroup", method = RequestMethod.GET)
    public @ResponseBody
    List<TourGroup> getTourGroups(@RequestHeader(value="X-AUTH-TOKEN") String token){
        return tourGroupDao.getTourGroups(Converter.fromJSON(Converter.fromBase64(token)).getCompanyId());
    }

    @RequestMapping(value = "/api/tourgroup/{tourgroupid}", method = RequestMethod.GET)
    public @ResponseBody TourGroup getTourGroup(@PathVariable(value="tourgroupid")int tourGroupId){
        return tourGroupDao.getTourGroup(tourGroupId);
    }

    @RequestMapping(value = "/api/tourgroup", method = RequestMethod.PUT)
    public @ResponseBody TourGroup updateTourGroup(@RequestBody TourGroup tourGroup){
        return tourGroupDao.updateTourGroup(tourGroup);
    }

    @RequestMapping(value = "/api/tourgroup/{tourgroupid}", method = RequestMethod.DELETE)
    public @ResponseBody void deleteTourGroup(@PathVariable(value="tourgroupid") int tourGroupId){
        tourGroupDao.deleteTourGroup(tourGroupId);
    }

    @RequestMapping(value = "/api/tourgroup", method = RequestMethod.POST)
    public @ResponseBody TourGroup createTourGroup(@RequestBody TourGroup tourGroup){
        return tourGroupDao.createTourGroup(tourGroup);
    }*/
}
