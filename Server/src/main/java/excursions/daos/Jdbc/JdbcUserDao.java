package excursions.daos.Jdbc;

import excursions.daos.UserDao;
import excursions.models.User;
import excursions.security.UserAuthority;
import excursions.utils.DuplicateUserException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcUserDao implements UserDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbc;
	
    @Override
    public User findUserByUsername(String username) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username",username);

        //Get the user
        String sql = "SELECT id, username, password, company_id AS companyId FROM user WHERE username = :username";
        User user = jdbc.queryForObject(sql, params, new BeanPropertyRowMapper<>(User.class));

        //Get user permissions
        params.addValue("id", user.getId());
        sql = "SELECT description FROM user_privileges up, privilege p WHERE up.user_id = :id AND up.privilege_id = p.id";
        List<String> authorities = jdbc.queryForList(sql, params, String.class);

        for(String authority : authorities){
            UserAuthority userAuthority = new UserAuthority();
            userAuthority.setAuthority(authority);
            user.getAuthorities().add(userAuthority);
        }

        return user;
    }

    @Override
    public User createUser(User user){
        //Create a hash from the users password
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        //Set params attributes from user and execute the sql
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(user);
        String sql = "INSERT INTO user(username,password,company_id) VALUES(:username,:password,:companyId)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		try {
            jdbc.update(sql, params, keyHolder);
        }catch(DataAccessException e){
            throw new DuplicateUserException();
        }

        user.setId(keyHolder.getKey().intValue());

        //Get the user privlege id
        MapSqlParameterSource mappedParams = new MapSqlParameterSource();
        mappedParams.addValue("userId", user.getId());
        sql = "SELECT id FROM privilege WHERE description = 'user'";
        int privilegeId = jdbc.queryForObject(sql, params, Integer.class);
        mappedParams.addValue("privilegeId", privilegeId);

        //Add user privilege to every user
        sql = "INSERT INTO user_privileges(user_id, privilege_id) VALUES(:userId, :privilegeId)";
        jdbc.update(sql, mappedParams);

        //Get user permissions
        sql = "SELECT description FROM user_privileges up, privilege p WHERE up.user_id = :userId AND up.privilege_id = p.id";
        List<String> authorities = jdbc.queryForList(sql, mappedParams, String.class);

        for(String authority : authorities){
            UserAuthority userAuthority = new UserAuthority();
            userAuthority.setAuthority(authority);
            user.getAuthorities().add(userAuthority);
        }

        return user;
    }
}
