package ru.job4j.url.shortcut.details;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.url.shortcut.repository.SiteRepository;

@Service
@AllArgsConstructor
public class SiteDetailsServiceImpl implements UserDetailsService {
    SiteRepository siteRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        var user = siteRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Site Not Found with login: " + login);
        }
        return SiteDetailsImpl.build(user);
    }
}
