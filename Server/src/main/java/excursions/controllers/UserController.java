package excursions.controllers;

import excursions.daos.UserDao;
import excursions.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/api/user", method = RequestMethod.POST)
    public @ResponseBody User createUser(@RequestBody User user){
        return userDao.createUser(user);
    }
}