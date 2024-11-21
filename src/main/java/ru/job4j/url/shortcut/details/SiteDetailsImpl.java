package ru.job4j.url.shortcut.details;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.job4j.url.shortcut.model.Site;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class SiteDetailsImpl implements UserDetails {

    private Long id;
    private String siteName;
    private String login;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    public SiteDetailsImpl(Long id, String siteName, String login, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.siteName = siteName;
        this.login = login;
        this.password = password;
        this.authorities = authorities;
    }
    public static SiteDetailsImpl build(Site site) {
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_USER")
        );
        return new SiteDetailsImpl(site.getId(),
                site.getSiteName(),
                site.getLogin(),
                site.getPassword(),
                authorities);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    public Long getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return siteName;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SiteDetailsImpl site = (SiteDetailsImpl) o;
        return Objects.equals(id, site.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, siteName, login, password, authorities);
    }
}