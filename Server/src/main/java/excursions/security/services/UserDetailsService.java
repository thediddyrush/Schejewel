package excursions.security.services;

import excursions.daos.UserDao;
import excursions.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{

    @Autowired
    private UserDao userDao;

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userDao.findUserByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }

        detailsChecker.check(user);
        return user;
    }
}
