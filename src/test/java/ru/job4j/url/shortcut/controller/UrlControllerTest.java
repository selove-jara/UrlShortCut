package ru.job4j.url.shortcut.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.job4j.url.shortcut.dto.UrlRequest;
import ru.job4j.url.shortcut.dto.UrlResponse;
import ru.job4j.url.shortcut.model.Url;
import ru.job4j.url.shortcut.repository.SiteRepository;
import ru.job4j.url.shortcut.service.UrlService;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UrlControllerTest {

    @Mock
    private SiteRepository siteRepository;

    @Mock
    private UrlService urlService;

    @InjectMocks
    private UrlController urlController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenConvertUrlThenReturnsShortCode() {
        String login = "testLogin";
        String originalUrl = "https://example.com";
        String shortCode = "abc123";

        UrlRequest urlRequest = new UrlRequest(originalUrl, login);
        when(siteRepository.findByLogin(login)).thenReturn(null);
        when(urlService.convertUrl(originalUrl, null)).thenReturn(shortCode);

        ResponseEntity<?> response = urlController.convertUrl(urlRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        UrlResponse urlResponse = (UrlResponse) response.getBody();
        assertThat(urlResponse).isNotNull();
        assertThat(urlResponse.getCode()).isEqualTo(shortCode);

        verify(siteRepository, times(1)).findByLogin(login);
        verify(urlService, times(1)).convertUrl(originalUrl, null);
    }

    @Test
    void whenRedirectToUrlThenReturnsOriginalUrl() {
        String shortCode = "abc123";
        String originalUrl = "https://example.com";

        when(urlService.findByShortCode(shortCode)).thenReturn(originalUrl);

        ResponseEntity<?> response = urlController.redirectToUrl(shortCode);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(originalUrl);

        verify(urlService, times(1)).findByShortCode(shortCode);
        verify(urlService, times(1)).increaseCallCount(shortCode);
    }

    @Test
    void whenRedirectToUrlThenReturnsNotFound() {
        String shortCode = "invalidCode";

        when(urlService.findByShortCode(shortCode)).thenReturn("");

        ResponseEntity<?> response = urlController.redirectToUrl(shortCode);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        verify(urlService, times(1)).findByShortCode(shortCode);
        verify(urlService, never()).increaseCallCount(shortCode);
    }

    @Test
    void whenGetStatisticThenReturnsUrlStatistics() {
        Url url1 = new Url();
        url1.setOriginalUrl("https://example1.com");
        url1.setCallCount(10L);

        Url url2 = new Url();
        url2.setOriginalUrl("https://example2.com");
        url2.setCallCount(5L);

        when(urlService.getAllUrlsWithStats()).thenReturn(List.of(url1, url2));

        List<Object> statistics = urlController.getStatistic();

        assertThat(statistics).hasSize(2);
        assertThat(statistics).containsExactlyInAnyOrder(
                Map.of("url", "https://example1.com", "total", 10L),
                Map.of("url", "https://example2.com", "total", 5L)
        );

        verify(urlService, times(1)).getAllUrlsWithStats();
    }
}