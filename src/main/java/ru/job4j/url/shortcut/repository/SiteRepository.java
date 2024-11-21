package ru.job4j.url.shortcut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.url.shortcut.model.Site;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {

    Site findByLogin(String login);

    boolean existsBySiteName(String siteName);

    Site findBySiteName(String siteName);
}
