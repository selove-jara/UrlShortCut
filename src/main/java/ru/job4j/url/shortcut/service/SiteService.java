package ru.job4j.url.shortcut.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.url.shortcut.dto.SiteDTO;
import ru.job4j.url.shortcut.model.Site;
import ru.job4j.url.shortcut.repository.SiteRepository;

import java.util.UUID;

@Service
public class SiteService {

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public SiteDTO registerSite(String siteName) {
        if (siteRepository.existsBySiteName(siteName)) {
            return new SiteDTO(false, null, null);
        }

        String login = UUID.randomUUID().toString();
        String rawPassword = UUID.randomUUID().toString();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        System.out.println("Login: " + login);
        System.out.println("Raw password: " + rawPassword);

        Site site = new Site();
        site.setSiteName(siteName);
        site.setLogin(login);
        site.setPassword(encodedPassword);
        siteRepository.save(site);
        return new SiteDTO(true, login, rawPassword);
    }
}
