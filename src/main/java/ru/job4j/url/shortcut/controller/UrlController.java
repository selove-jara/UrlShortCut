package ru.job4j.url.shortcut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.url.shortcut.dto.UrlRequest;
import ru.job4j.url.shortcut.dto.UrlResponse;
import ru.job4j.url.shortcut.model.Url;
import ru.job4j.url.shortcut.repository.SiteRepository;
import ru.job4j.url.shortcut.service.UrlService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UrlController {

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private UrlService urlService;

    @PostMapping("/convert")
    public ResponseEntity<?> convertUrl(@RequestBody UrlRequest urlRequest) {
        return ResponseEntity.ok(new UrlResponse(
                urlService.convertUrl(urlRequest.getUrl(),
                        siteRepository.findByLogin(urlRequest.getLogin()))));
    }

    @GetMapping("/redirect/{shortCode}")
    public ResponseEntity<?> redirectToUrl(@PathVariable String shortCode) {
        var url = urlService.findByShortCode(shortCode);
        if (!url.isEmpty()) {
            urlService.increaseCallCount(shortCode);
            return ResponseEntity.ok(url);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/statistic")
    public List<Object> getStatistic() {
        List<Url> urls = urlService.getAllUrlsWithStats();
        return urls.stream()
                .map(url -> Map.of("url", url.getOriginalUrl(), "total", url.getCallCount()))
                .collect(Collectors.toList());
    }
}
