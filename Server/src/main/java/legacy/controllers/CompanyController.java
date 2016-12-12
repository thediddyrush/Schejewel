package legacy.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class CompanyController {
    /*@Autowired
    private CompanyDao companyDao = new JdbcCompanyDao();

    @RequestMapping(value = "/api/company/{companyid}", method = RequestMethod.GET)
    public @ResponseBody
    Company getCompany(@PathVariable(value="companyid")int companyId){
        return companyDao.getCompany(companyId);
    }

    @RequestMapping(value = "/api/company", method = RequestMethod.PUT)
    public @ResponseBody Company updateCompany(@RequestBody Company company){
        return companyDao.updateCompany(company);
    }

    @RequestMapping(value = "/api/company/{companyid}", method = RequestMethod.DELETE)
    public @ResponseBody void deleteCompany(@PathVariable(value="companyid") int companyid){
        companyDao.deleteCompany(companyid);
    }

    @RequestMapping(value = "/api/company", method = RequestMethod.POST)
    public @ResponseBody Company createCompany(@RequestBody Company company){
        return companyDao.createCompany(company);
    }*/
}