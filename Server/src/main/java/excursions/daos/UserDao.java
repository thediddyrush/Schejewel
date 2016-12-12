package excursions.daos;

import excursions.models.User;

public interface UserDao {
    public User findUserByUsername(String username);
    public User createUser(User user);
}
