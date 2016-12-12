package legacy.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import excursions.security.UserAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public class User implements UserDetails {
    private long expires;
    private Set<UserAuthority> authorities;
    private int id;
    private String username;
    private int companyId;
    private String password;
    @JsonIgnore
    private boolean accountExpired;
    @JsonIgnore
    private boolean accountLocked;
    @JsonIgnore
    private boolean credentialsExpired;
    @JsonIgnore
    private boolean accountEnabled;

    public User(){}

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<UserAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<UserAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return id;
    }

    public void setUserId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return !accountEnabled;
    }
}
