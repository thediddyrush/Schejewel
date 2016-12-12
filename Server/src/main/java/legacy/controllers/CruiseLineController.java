package legacy.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class CruiseLineController {
    /*
    @Autowired
    private CruiseLineDao cruiseLineDao = new JdbcCruiseLineDao();

    @RequestMapping(value = "/api/cruiseline", method = RequestMethod.GET)
    public @ResponseBody
    List<CruiseLine> getCrusieLines(){
        return cruiseLineDao.getCruiseLines();
    }

    @RequestMapping(value = "/api/cruiseline/{cruiselineid}", method = RequestMethod.GET)
    public @ResponseBody CruiseLine getCruiseLine(@PathVariable(value="cruiselineid")int cruiseLineId){
        return cruiseLineDao.getCruiseLine(cruiseLineId);
    }

    @RequestMapping(value = "/api/cruiseline", method = RequestMethod.PUT)
    public @ResponseBody CruiseLine updateCruiseLine(@RequestBody CruiseLine cruiseLine){
        return cruiseLineDao.updateCruiseLine(cruiseLine);
    }

    @RequestMapping(value = "/api/cruiseline/{cruiselineid}", method = RequestMethod.DELETE)
    public @ResponseBody void deleteCruiseLine(@PathVariable(value="cruiselineid") int cruiseLineId){
        cruiseLineDao.deleteCruiseLine(cruiseLineId);
    }

    @RequestMapping(value = "/api/cruiseline", method = RequestMethod.POST)
    public @ResponseBody CruiseLine createCruiseLine(@RequestBody CruiseLine cruiseLine){
        return cruiseLineDao.createCruiseLine(cruiseLine);
    }*/
}