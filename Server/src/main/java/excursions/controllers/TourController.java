package excursions.controllers;

import excursions.daos.ScheduleConflictDao;
import excursions.daos.TourDao;
import excursions.models.Tour;
import excursions.utils.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TourController {
    @Autowired
    private TourDao tourDao;
    
    @Autowired
    private ScheduleConflictDao scheduleConflictDao;

    @RequestMapping(value = "/api/tours", method = RequestMethod.GET)
    public @ResponseBody List<Tour> getTours(@RequestHeader(value="X-AUTH-TOKEN") String token){
        return tourDao.getTours(Converter.fromJSON(Converter.fromBase64(token)).getCompanyId());
    }

    @RequestMapping(value = "/api/tour", method = RequestMethod.PUT)
    public @ResponseBody Tour updateTour(@RequestBody Tour tour){
        Tour temp = tourDao.updateTour(tour);
        scheduleConflictDao.detect();
        return temp;
    }

    @RequestMapping(value = "/api/tour/{tourid}", method = RequestMethod.DELETE)
    public @ResponseBody void deleteTour(@PathVariable(value="tourid") int tourId){
        tourDao.deleteTour(tourId);
        scheduleConflictDao.detect();
        return;
    }

    @RequestMapping(value = "/api/tour", method = RequestMethod.POST)
    public @ResponseBody Tour createTour(@RequestBody Tour tour){
    	Tour temp = tourDao.createTour(tour);
    	scheduleConflictDao.detect();
    	return temp;
    }
}
