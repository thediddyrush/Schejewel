package legacy.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class PortageController {/*
    @Autowired
    private PortageDao portageDao = new JdbcPortageDao();

    @RequestMapping(value = "/api/portage", method = RequestMethod.GET)
    public @ResponseBody
    List<Portage> getPortages(@RequestHeader(value="X-AUTH-TOKEN") String token){
        return portageDao.getPortages(Converter.fromJSON(Converter.fromBase64(token)).getCompanyId());
    }

    @RequestMapping(value = "/api/portage/{portageid}", method = RequestMethod.GET)
    public @ResponseBody Portage getPortage(@PathVariable(value="portageid")int portageId){
        return portageDao.getPortage(portageId);
    }

    @RequestMapping(value = "/api/portage", method = RequestMethod.PUT)
    public @ResponseBody Portage updatePortage(@RequestBody Portage portage){
        return portageDao.updatePortage(portage);
    }

    //Todo: Sql out of date
    @RequestMapping(value = "/api/portage/{portageid}", method = RequestMethod.DELETE)
    public @ResponseBody void deletePortage(@PathVariable(value="portageid") int portageId){
        portageDao.deletePortage(portageId);
    }

    @RequestMapping(value = "/api/portage", method = RequestMethod.POST)
    public @ResponseBody Portage createPortage(@RequestBody Portage portage){
        return portageDao.createPortage(portage);
    }*/
}