package ru.job4j.url.shortcut.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import ru.job4j.url.shortcut.dto.SiteDTO;
import ru.job4j.url.shortcut.service.SiteService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SiteControllerTest {

    @Autowired
    private SiteController siteController;

    @MockBean
    private SiteService siteService;

    @Test
    void whenRegisterSiteSuccessfullyThenReturnOkResponse() {
        String siteName = "example.com";
        SiteDTO siteDTO = new SiteDTO(true, "someLogin", "somePassword");

        when(siteService.registerSite(siteName)).thenReturn(siteDTO);

        ResponseEntity<SiteDTO> response = siteController.register(siteName);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isRegistration());
        assertEquals("someLogin", response.getBody().getLogin());
        assertEquals("somePassword", response.getBody().getPassword());
    }

    @Test
    void whenRegisterSiteFailsThenReturnBadRequestResponse() {
        String siteName = "invalid.com";
        SiteDTO siteDTO = new SiteDTO(false, "", "");

        when(siteService.registerSite(siteName)).thenReturn(siteDTO);

        ResponseEntity<SiteDTO> response = siteController.register(siteName);

        assertEquals(400, response.getStatusCodeValue());
        assertFalse(response.getBody().isRegistration());
        assertEquals("", response.getBody().getLogin());
        assertEquals("", response.getBody().getPassword());
    }
}