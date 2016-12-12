package excursions.security;

import excursions.models.User;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthority implements GrantedAuthority {

    private User user;
    private String authority;

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority){
        this.authority = authority;
    }
}
