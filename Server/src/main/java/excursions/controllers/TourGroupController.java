package excursions.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import excursions.daos.ScheduleConflictDao;
import excursions.daos.TourGroupDao;
import excursions.models.TourGroup;

public class TourGroupController {
    @Autowired
    private TourGroupDao tourGroupDao;
    
    @Autowired
    private ScheduleConflictDao scheduleConflictDao;
	
    @RequestMapping(value = "/api/tourGroup", method = RequestMethod.POST)
    public @ResponseBody TourGroup createTour(@RequestBody TourGroup tourGroup){
    	TourGroup temp = tourGroupDao.createTourGroup(tourGroup);
    	scheduleConflictDao.detect();
    	return temp;
    }
}
