package legacy.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    /*
    @Autowired
    private UserDao userDao = new JdbcUserDao();

    @RequestMapping(value = "/api/user", method = RequestMethod.POST)
    public @ResponseBody
    User createUser(@RequestBody User user){
        return userDao.createUser(user);
    }

    @RequestMapping(value = "/api/user", method = RequestMethod.PUT)
    public @ResponseBody User updateUser(@RequestBody User user){
        return userDao.updateUser(user);
    }

    @RequestMapping(value = "/api/user/{userid}", method = RequestMethod.DELETE)
    public @ResponseBody void deleteUser(@PathVariable(value="userid") int userId){
        userDao.deleteUser(userId);
    }*/
}