package legacy.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class TourController {
    /*
    @Autowired
    private TourDao tourDao = new JdbcTourDao();

    @RequestMapping(value = "/api/tour", method = RequestMethod.GET)
    public @ResponseBody
    List<Tour> getTours(@RequestHeader(value="X-AUTH-TOKEN") String token){
        return tourDao.getTours(Converter.fromJSON(Converter.fromBase64(token)).getCompanyId());
    }

    @RequestMapping(value = "/api/tour/{tourid}", method = RequestMethod.GET)
    public @ResponseBody Tour getTour(@PathVariable(value="tourid")int tourId){
        return tourDao.getTour(tourId);
    }

    @RequestMapping(value = "/api/tour", method = RequestMethod.PUT)
    public @ResponseBody Tour updateTour(@RequestBody Tour tour){
        return tourDao.updateTour(tour);
    }

    @RequestMapping(value = "/api/tour/{tourid}", method = RequestMethod.DELETE)
    public @ResponseBody void deleteTour(@PathVariable(value="tourid") int tourId){
        tourDao.deleteTour(tourId);
    }

    @RequestMapping(value = "/api/tour", method = RequestMethod.POST)
    public @ResponseBody Tour createTour(@RequestBody Tour tour){
        return tourDao.createTour(tour);
    }*/
}