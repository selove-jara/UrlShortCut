package ru.job4j.url.shortcut.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.url.shortcut.model.Url;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {


    @Query("SELECT u FROM Url u WHERE u.shortCode = :shortCode")
    Optional<Url> findOriginalUrlByShortCode(@Param("shortCode") String shortCode);

    boolean existsByOriginalUrl(String originalUrl);

    Optional<Url> findByOriginalUrl(String originalUrl);

    @Query("SELECT u FROM Url u")
    List<Url> findAllUrlsWithStats();

    @Modifying
    @Transactional
    @Query("UPDATE Url u SET u.callCount = u.callCount + 1 WHERE u.shortCode = :shortCode")
    void updateCallCountByShortCode(@Param("shortCode") String shortCode);
}
