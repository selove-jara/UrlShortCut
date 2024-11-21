package ru.job4j.url.shortcut.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.url.shortcut.model.Site;
import ru.job4j.url.shortcut.model.Url;
import ru.job4j.url.shortcut.repository.UrlRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public String convertUrl(String originalUrl, Site site) {
        Optional<Url> existingUrl = urlRepository.findByOriginalUrl(originalUrl);
        if (existingUrl.isPresent()) {
            return existingUrl.get().getShortCode();
        }

        String shortCode = generateShortCode();

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortCode(shortCode);
        url.setSite(site);
        urlRepository.save(url);

        return shortCode;
    }

    private String generateShortCode() {
        return UUID.randomUUID().toString().substring(0, 7);
    }

    public String findByShortCode(String shortCode) {
        var url = urlRepository.findOriginalUrlByShortCode(shortCode)
                .orElseThrow(() -> new NoSuchElementException("This url not found"));
        return url.getOriginalUrl();
    }

    @Transactional
    public void increaseCallCount(String shortCode) {
        urlRepository.updateCallCountByShortCode(shortCode);
    }


    public List<Url> getAllUrlsWithStats() {
        return urlRepository.findAllUrlsWithStats();
    }
}
